package com.application.foodapp


data class OrderItems(
    var item: FoodItem = FoodItem("","",""),
    var quantity: Int = 0,
    var isDeliverd: Boolean = false,
)