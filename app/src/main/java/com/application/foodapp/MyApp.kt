package com.application.foodapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData

class MyApp: Application() {
    var foodItems:ArrayList<FoodItemsData> = ArrayList()
//    fun clearDrinksItems(){
//        drinkItems!!.clear()
//    }
    var cancelOrderOrNot:Boolean = false

    var dismissOrNot:Boolean = false

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}