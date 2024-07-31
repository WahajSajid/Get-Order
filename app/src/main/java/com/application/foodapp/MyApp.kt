package com.application.foodapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MyApp : Application() {
    var foodItems: ArrayList<OrderItems> = ArrayList()
    var cancelOrderOrNot: Boolean = false

    var dismissOrNot: Boolean = false

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}