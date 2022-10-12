package com.mitocode.marketapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketapp.data.server.RegisterAccountRequest
import com.mitocode.marketapp.domain.Gender
import com.mitocode.marketapp.domain.User
import com.mitocode.marketapp.usescases.GetGender
import com.mitocode.marketapp.usescases.RegisterAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val getGender: GetGender,
                                            private val registerAccount: RegisterAccount
)
    : ViewModel() {

    private val _state = MutableLiveData<RegisterAccountState>(RegisterAccountState.Init)
    val state: LiveData<RegisterAccountState> = _state

    init {
        viewModelScope.launch {

            _state.value = RegisterAccountState.IsLoading(true)

            try {
                val response = withContext(Dispatchers.IO){
                    getGender()
                }

                response.fold(
                    { error ->
                        _state.value = RegisterAccountState.Error(error.toString())
                    },
                    { genders ->
                        _state.value = RegisterAccountState.SuccessGenders(genders)
                    }
                )

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
                    registerAccount(request)
                }

                response.fold(
                    { error ->
                        _state.value = RegisterAccountState.Error(error.toString())
                    },
                    { user ->
                        _state.value = RegisterAccountState.SuccessRegister(user)
                    }
                )

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
        data class SuccessGenders(val genders: List<Gender>): RegisterAccountState()
        data class SuccessRegister(val user: User): RegisterAccountState()

    }

}