package com.application.foodapp

class ExistingOrder {
    var item: FoodItem? = null
    var quantity: Int? = null
    var isDeliverd: Boolean? = false
    constructor(){}
    constructor(item:FoodItem,quantity:Int,isDeliverd:Boolean){
        this.isDeliverd = isDeliverd
        this.item = item
        this.quantity = quantity
    }
}