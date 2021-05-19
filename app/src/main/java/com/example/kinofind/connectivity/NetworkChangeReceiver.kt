package com.example.kinofind.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.kinofind.R

class NetworkChangeReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {

        val build = NotificationCompat.Builder(context, "some_channel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Connectivity service")
                .setContentText(if (isInternetAvailable(context)) "true" else "false")
                .build()

        with(NotificationManagerCompat.from(context)) {
            notify(22, build)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isInternetAvailable(context: Context): Boolean {
        val cm = (context.getSystemService(Context.CONNECTIVITY_SERVICE) ?: return false) as ConnectivityManager
        val cap = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }
}