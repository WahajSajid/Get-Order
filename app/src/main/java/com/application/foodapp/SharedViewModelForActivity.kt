package com.application.foodapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModelForActivity:ViewModel() {
    private val _foodItems = MutableLiveData<ArrayList<FoodItemsData>>()
    val foodItems: LiveData<ArrayList<FoodItemsData>> = _foodItems

    fun setFoodItems(value: ArrayList<FoodItemsData>) {
        _foodItems.value = value
    }
}