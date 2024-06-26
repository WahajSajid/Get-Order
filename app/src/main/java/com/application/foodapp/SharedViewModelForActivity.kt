package com.application.foodapp


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModelForActivity : ViewModel() {
//    private val _foodItems = ArrayList<OrderItemsData>()
//    var foodItems: ArrayList<OrderItemsData> = _foodItems


    private val text = MutableLiveData<String>()
    var _text = text

//    fun setFoodItems(value: ArrayList<FoodItemsData>) {
//        foodItems.addAll(value)
//    }
}