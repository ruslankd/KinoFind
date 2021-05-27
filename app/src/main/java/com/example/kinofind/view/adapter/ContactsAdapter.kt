package com.example.kinofind.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kinofind.databinding.ItemContactBinding
import com.example.kinofind.model.entities.Contact
import com.example.kinofind.view.ContactsFragment

class ContactsAdapter(val listener: ContactsFragment.OnItemViewClickListener) :
        RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private lateinit var binding: ItemContactBinding

    private var contactList = ArrayList<Contact>()

    fun setData(data: ArrayList<Contact>) {
        contactList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contactList[position])
    }

    override fun getItemCount() = contactList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(contact: Contact) = with(binding) {
            tvName.text = contact.name
            tvPhone.text = contact.phone
            root.setOnClickListener {
                listener.onItemViewClick(contact.phone)
            }
        }

    }
}