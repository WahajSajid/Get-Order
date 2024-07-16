package com.application.foodapp

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import java.util.concurrent.TimeUnit

interface HasInternetAccessCallback{
    fun onInternetAccessAvailable()
    fun onInternetAccessNotAvailable()
}


object HasInternetAccess {
    fun hasInternetAccess(callback:HasInternetAccessCallback){
        val client = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url("https://www.google.com")
            .build()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                callback.onInternetAccessNotAvailable()
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onInternetAccessAvailable()
            }
        })
    }
}