package com.application.foodapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel() {

    //Table No
    private var _tableNo = MutableLiveData<String>("")
    var tableNo: MutableLiveData<String> = _tableNo

}