package com.example.kinofind.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinofind.R
import com.example.kinofind.databinding.ContactsFragmentBinding
import com.example.kinofind.model.entities.Contact
import com.example.kinofind.view.adapter.ContactsAdapter


class ContactsFragment : Fragment() {

    private lateinit var binding: ContactsFragmentBinding

    private lateinit var contactsAdapter: ContactsAdapter
    private var callPermission = false

    companion object {
        const val REQUEST_CODE_CONTACTS = 97
        const val REQUEST_CODE_CALL = 98
        fun newInstance() = ContactsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = ContactsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactsAdapter = ContactsAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(phone: String) {
                if (callPermission) {
                    val callIntent = Intent(Intent.ACTION_CALL)
                    callIntent.data = Uri.parse("tel:$phone")
                    startActivity(callIntent)
                } else {
                    Toast.makeText(context, getString(R.string.permission), Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.rvContacts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = contactsAdapter
        }

        checkPermission()
    }

    private fun checkCallPermission() {
        context?.let {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(it, Manifest.permission.CALL_PHONE) -> {
                    callPermission = true
                }
                else -> {
                    requestCallPermission()
                }
            }
        }
    }

    private fun checkPermission() {
        context?.let {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) -> {
                    getContacts()
                    checkCallPermission()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE_CONTACTS)
    }

    private fun requestCallPermission() {
        requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CODE_CALL)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_CONTACTS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getContacts()
                    checkCallPermission()
                }
            }
            REQUEST_CODE_CALL -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    callPermission = true
                }
            }
        }
    }

    @SuppressLint("Recycle")
    private fun getContacts() {
        context?.let {
            val cursorWithContacts: Cursor? = it.contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    null,
                    null,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )

            cursorWithContacts?.let { cursor ->
                val data = ArrayList<Contact>()

                val cr: ContentResolver = context!!.contentResolver
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        val name = cursor
                                .getString(
                                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                                )
                        val id: String = cursor.getString(
                                cursor.getColumnIndex(ContactsContract.Contacts._ID))

                        var phone = getString(R.string.default_phone)
                        if (cursor.getInt(cursor.getColumnIndex(
                                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                            val pCur: Cursor? = cr.query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null)
                            pCur.let {
                                it!!.moveToFirst()
                                phone = it.getString(it.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER))
                                it.close()
                            }
                        }
                        data.add(Contact(name, phone))
                    }
                }
                addContacts(data)
            }
            cursorWithContacts?.close()
        }
    }

    private fun addContacts(data: ArrayList<Contact>) {
        contactsAdapter.setData(data)
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(phone: String)
    }

}