package com.mitocode.marketapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketapp.data.Api
import com.mitocode.marketapp.data.GenderRemote
import com.mitocode.marketapp.data.RegisterAccountRequest
import com.mitocode.marketapp.data.UserRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {

    private val _state = MutableLiveData<RegisterAccountState>(RegisterAccountState.Init)
    val state: LiveData<RegisterAccountState> = _state

    init {
        viewModelScope.launch {

            _state.value = RegisterAccountState.IsLoading(true)

            try {
                val response = withContext(Dispatchers.IO){
                    Api.build().getGenders()
                }

                if (response.isSuccessful){

                    val responseData = response.body()
                    responseData?.data?.let { genders ->
                        _state.value = RegisterAccountState.SuccessGenders(genders)
                    }

                }else{
                    _state.value = RegisterAccountState.Error(response.message().toString())
                }

            }catch (ex: Exception){
                _state.value = RegisterAccountState.Error(ex.message.toString())
            }finally {
                _state.value = RegisterAccountState.IsLoading(false)
            }

        }
    }

    fun createAccount(request: RegisterAccountRequest) {
        viewModelScope.launch {

            _state.value = RegisterAccountState.IsLoading(true)

            try {
                val response = withContext(Dispatchers.IO){
                    Api.build().registerAccount(request)
                }

                if (response.isSuccessful){

                    val responseData = response.body()

                    responseData?.let{
                        if(it.success){
                            _state.value = RegisterAccountState.SuccessRegister(it.data!!)
                        }else{
                            _state.value = RegisterAccountState.Error(it.message)
                        }
                    }

                }else{
                    _state.value = RegisterAccountState.Error(response.message().toString())
                }

            }catch (ex: Exception){
                _state.value = RegisterAccountState.Error(ex.message.toString())
            }finally {
                _state.value = RegisterAccountState.IsLoading(false)
            }

        }
    }

    sealed class RegisterAccountState {

        object Init : RegisterAccountState()
        data class IsLoading(val isLoading: Boolean): RegisterAccountState()
        data class Error(val rawResponse: String): RegisterAccountState()
        data class SuccessGenders(val genders: List<GenderRemote>): RegisterAccountState()
        data class SuccessRegister(val user: UserRemote): RegisterAccountState()

    }

}