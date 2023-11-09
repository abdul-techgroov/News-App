package com.news.app.snippet

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

fun ViewModel.debounce(
    waitMs: Long = 1000L,
    debounceJob: Job?,
    destinationFunction: () -> Unit
): Job? {
    debounceJob?.cancel()
    return this.viewModelScope.launch {
        delay(waitMs)
        destinationFunction()
    }
}