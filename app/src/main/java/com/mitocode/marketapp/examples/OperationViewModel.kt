package com.mitocode.marketapp.examples

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OperationViewModel : ViewModel() {

    // LiveData - MutableLiveData
    private val _number : MutableLiveData<Int> = MutableLiveData(0)
    val number: LiveData<Int> = _number

    fun setNumber(numberInput: Int){
        _number.value = numberInput
    }



}