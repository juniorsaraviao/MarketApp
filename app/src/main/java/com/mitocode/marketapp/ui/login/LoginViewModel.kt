package com.mitocode.marketapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketapp.data.Api
import com.mitocode.marketapp.data.LoginRequest
import com.mitocode.marketapp.data.UserRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    private val _loader: MutableLiveData<Boolean> = MutableLiveData()
    val loader: LiveData<Boolean> = _loader

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _user: MutableLiveData<UserRemote> = MutableLiveData()
    val user: LiveData<UserRemote> = _user

    fun auth(email: String, password: String){

        viewModelScope.launch {

            _loader.value = true

            try {
                // Add suspend on Api to wait for the response
                val response = withContext(Dispatchers.IO){
                    Api.build().auth(LoginRequest(email, password))
                }

                if (response.isSuccessful){

                    val response = response.body()
                    response?.let {
                        _user.value = it.data!!
                    }

                }else{
                    _error.value = response.toString()
                }
            }catch (ex: Exception){
                _error.value = ex.message.toString()
            }finally {
                _loader.value = false
            }
        }

    }

}