package com.example.kinofind.view.services

import android.app.Service
import com.google.firebase.messaging.FirebaseMessagingService

open class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}