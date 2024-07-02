package com.application.foodapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel() {

    //Table No
    private var _tableName = MutableLiveData<String>("")
    var tableName: MutableLiveData<String> = _tableName


    private var _dismissOrNot = MutableLiveData<Boolean>()
    var dismissOrNot = _dismissOrNot

    //Array list of orderItems
    private var _orderTimes = ArrayList<OrderItems>()
    var orderItems = _orderTimes

}