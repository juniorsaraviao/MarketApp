package com.mitocode.marketapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketapp.domain.User
import com.mitocode.marketapp.usescases.RequestAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor(private val requestAuth: RequestAuth): ViewModel() {

    /* private val _loader: MutableLiveData<Boolean> = MutableLiveData()
    val loader: LiveData<Boolean> = _loader

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _user: MutableLiveData<UserRemote> = MutableLiveData()
    val user: LiveData<UserRemote> = _user */

    private val _state: MutableLiveData<LoginState> = MutableLiveData(LoginState.Init)
    val state: LiveData<LoginState> = _state

    fun auth(email: String, password: String){

        viewModelScope.launch {

            //_loader.value = true
            _state.value = LoginState.IsLoading(true)

            try {

                val response = withContext(Dispatchers.IO){
                    requestAuth(email, password, "")
                }

                response.fold(
                    { error ->
                        _state.value = LoginState.Error(error.toString())
                    },
                    { user ->
                        _state.value = LoginState.Success(user)
                    }
                )

            } catch (ex: Exception){
                //_error.value = ex.message.toString()
                _state.value = LoginState.Error(ex.message.toString())
            } finally {
                //_loader.value = false
                _state.value = LoginState.IsLoading(false)
            }

            /* try {
                // Add suspend on Api to wait for the response
                val response = withContext(Dispatchers.IO){
                    Api.build().auth(LoginRequest(email, password))
                }

                if (response.isSuccessful){

                    val response = response.body()
                    response?.let {
                        //_user.value = it.data!!
                        if (it.success){
                            _state.value = LoginState.Success(it.data!!)
                        }else{
                            _state.value = LoginState.Error(it.message)
                        }
                    }

                }else{
                    //_error.value = response.toString()
                    _state.value = LoginState.Error(response.message().toString())
                }
            }catch (ex: Exception){
                //_error.value = ex.message.toString()
                _state.value = LoginState.Error(ex.message.toString())
            }finally {
                //_loader.value = false
                _state.value = LoginState.IsLoading(false)
            } */
        }

    }

    sealed class LoginState {

        object Init: LoginState()
        // more generic
        data class IsLoading(val isLoading: Boolean): LoginState()
        data class Error(val rawResponse: String): LoginState()
        data class Success(val user: User): LoginState()

    }

}