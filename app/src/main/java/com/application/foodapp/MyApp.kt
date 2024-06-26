package com.application.foodapp

import android.app.Application

class MyApp: Application() {
    var foodItems:ArrayList<FoodItemsData>? =null
    var drinkItems:ArrayList<DrinksItemsData>? =null
    fun clearDrinksItems(){
        drinkItems!!.clear()
    }
}