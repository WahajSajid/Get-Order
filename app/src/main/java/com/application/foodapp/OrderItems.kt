package com.application.foodapp


data class OrderItems(
    var item: FoodItem = FoodItem("",0,""),
    var quantity: Int = 0,
    var deliverd: Boolean? = null,
)