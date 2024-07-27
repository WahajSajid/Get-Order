package com.application.foodapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel() {

    //Table No
    private var _tableName = MutableLiveData("")
    var tableName: MutableLiveData<String> = _tableName


}