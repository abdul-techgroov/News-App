package com.news.app.snippet

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.hasNetworkConnection(): Boolean {

    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    var isConnected = false

    // Retrieve current status of connectivity
    connectivityManager.allNetworks.forEach { network ->
        val networkCapability = connectivityManager.getNetworkCapabilities(network)

        networkCapability?.let {
            if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                isConnected = true
                return@forEach
            }
        }
    }

    return isConnected

}