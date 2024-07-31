package com.application.foodapp

data class OrderItems(
    var Item: FoodItem,
    var Quantity: Int,
    var IsDeliverd: Boolean = false,
)