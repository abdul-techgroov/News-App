package com.news.app.snippet

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.format.DateUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.app.db.entity.HeadlineEntity
import com.news.app.db.entity.NewsEntity
import com.news.app.model.NewsData
import com.news.app.navigation.PlaceHolder
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


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

fun String.getRoute(title: String) = this.replace(PlaceHolder.TITLE, title)

fun ByteArray.getBitmapFromByteArray(): Bitmap? =
    BitmapFactory.decodeByteArray(this, 0, this.size)

fun NewsData.getNewsEntity(imageByteArray: ByteArray?): NewsEntity = NewsEntity(
    title = this.title ?: "",
    description = this.description ?: "",
    time = this.time ?: "",
    image = if (((imageByteArray?.size?.toLong() ?: 0) / 1024) < 1024L) imageByteArray else null
)

fun NewsData.getHeadlineEntity(imageByteArray: ByteArray?): HeadlineEntity = HeadlineEntity(
    title = this.title ?: "",
    description = this.description ?: "",
    image = if (((imageByteArray?.size?.toLong() ?: 0) / 1024) < 1024L) imageByteArray else null
)

fun HeadlineEntity.getNewsData(): NewsData {
    val localImage = this.image?.getBitmapFromByteArray()
    return NewsData(
        this.title,
        this.description,
        image = localImage
    )
}

fun NewsEntity.getNewsData(): NewsData {
    val localImage = this.image?.getBitmapFromByteArray()
    return NewsData(
        this.title,
        this.description,
        time = this.time,
        image = localImage
    )
}

fun String.getByteArrayFromURL(): ByteArray? {
    return try {
        val url = URL(this)
        val connection =
            url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input = connection.inputStream
        val bitmap = BitmapFactory.decodeStream(input)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 20, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun String.getRelativeTimeSpanString(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")

    try {
        val date = dateFormat.parse(this)
        val currentTimeMillis = System.currentTimeMillis()
        val timeAgoMillis = date?.time ?: 0

        return DateUtils.getRelativeTimeSpanString(
            timeAgoMillis,
            currentTimeMillis,
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_RELATIVE
        ).toString()

    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}

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
