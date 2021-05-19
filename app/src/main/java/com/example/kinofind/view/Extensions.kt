package com.example.kinofind.view

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
        text: String?,
        actionText: String,
        action: ((View) -> Unit)? = null,
        length: Int = Snackbar.LENGTH_INDEFINITE
) {
    val ourSnackBar = Snackbar.make(this, text?: "", length)
    action?.let {
        ourSnackBar.setAction(actionText, it)
    }
    ourSnackBar.show()
}

fun View.showSnackBarWithoutAction(
        text: String,
        length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar
            .make(this, text, length)
            .show()
}

fun View.showSnackBarWithResource(
        textResource: Int,
        actionTextResource: Int,
        action: ((View) -> Unit)? = null,
        length: Int = Snackbar.LENGTH_INDEFINITE
) {
    val ourSnackBar = Snackbar.make(this, context.getString(textResource), length)
    action?.let {
        ourSnackBar.setAction(context.getString(actionTextResource), it)
    }
    ourSnackBar.show()
}