package com.example.synergym.utils

import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentActivity
import com.example.synergym.R
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

lateinit var ACTIVITY_ACTION: String
var SERVICE_PACKAGE = "com.example.biggie.network"

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE).also { snackbar ->
        snackbar.setAction("ok") {
            snackbar.dismiss()
        }
    }.show()
}

fun View.goneView() {
    visibility = View.GONE
}
fun View.inVisible() {
    visibility = View.INVISIBLE
}

fun TextView.labelsetText(name: String) {
    text = name
    setTextColor(resources.getColor(R.color.white))
}
fun TextView.setTypeFaceRobotoBold(context: Context) {
    val robotoBold = Typeface.createFromAsset(
        context.assets,
        "font/roboto-slab.bold.ttf"
    )
    typeface = robotoBold
}
fun TextView.setTypeFaceRobotoRegular(context: Context) {
    val regular = Typeface.createFromAsset(
        context.assets,
        "font/RobotoSlab-regular.ttf"
    )
    typeface = regular
}
fun getDate(publishedAt: String?): String {
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val date: Date =
        dateFormat.parse(publishedAt)

    val formatter: DateFormat =
        SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(date).toString()
}
fun format(text:String?): String {
    text?.replace("\r\n".toRegex(), "")
    return text!!
}
fun isnetworkAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        } else {
            connectivityManager.activeNetworkInfo.also {
                return it != null && it!!.isConnected
            }
        }

    }
    return result
}