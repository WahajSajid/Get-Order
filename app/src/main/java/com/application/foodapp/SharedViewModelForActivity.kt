package com.application.foodapp


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModelForActivity : ViewModel() {
    private val _foodItems = ArrayList<FoodItemsData>()
    var foodItems: ArrayList<FoodItemsData> = _foodItems


    private val text = MutableLiveData<String>()
    var _text = text

//    fun setFoodItems(value: ArrayList<FoodItemsData>) {
//        foodItems.addAll(value)
//    }
}