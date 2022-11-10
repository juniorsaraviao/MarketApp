package com.mitocode.marketapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailProductViewModel : ViewModel() {

    private val _number : MutableLiveData<Int> = MutableLiveData(0)
    val number: LiveData<Int> = _number

    fun addItem(){
        _number.value = _number.value!! + 1
    }

    fun subtractItem(){
        if (_number.value != 0) _number.value = _number.value!! - 1
        else _number.value = 0
    }
}