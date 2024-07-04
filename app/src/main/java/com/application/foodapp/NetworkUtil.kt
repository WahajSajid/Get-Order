@file:Suppress("DEPRECATION")

package com.application.foodapp

import android.content.Context
import android.net.ConnectivityManager
import java.net.HttpURLConnection
import java.net.URL

object NetworkUtil {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo!= null && activeNetworkInfo.isConnected
    }


}