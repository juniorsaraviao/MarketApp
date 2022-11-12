package com.mitocode.marketapp

import androidx.lifecycle.*
import com.mitocode.marketapp.data.server.RegisterCategoryRequest
import com.mitocode.marketapp.domain.PurchasedProduct
import com.mitocode.marketapp.usescases.RegisterCategory
import com.mitocode.marketapp.usescases.RegisterProduct
import com.mitocode.marketapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(private val registerProduct: RegisterProduct) : ViewModel() {

    private val _number : MutableLiveData<Int> = MutableLiveData(0)
    val number: LiveData<Int> = _number

    private val _state = MutableStateFlow<RegisterPurchaseState>(RegisterPurchaseState.Init)
    val state: StateFlow<RegisterPurchaseState> = _state.asStateFlow()

    fun addItem(){
        _number.value = _number.value!! + 1
    }

    fun subtractItem(){
        if (_number.value != 0) _number.value = _number.value!! - 1
        else _number.value = 0
    }

    fun savePurchase(request: PurchasedProduct){

        viewModelScope.launch {

            _state.value = RegisterPurchaseState.IsLoading(true)

            try {
                withContext(Dispatchers.IO){
                    registerProduct(request)
                }
            }catch (e: Exception){
                _state.value = RegisterPurchaseState.Error(e.message.toString())
            }finally {
                _state.value = RegisterPurchaseState.IsLoading(false)
                _state.value = RegisterPurchaseState.Success(Constants.detail_product_added)
            }
        }

    }

    sealed class RegisterPurchaseState {
        object Init: RegisterPurchaseState()
        data class IsLoading(val isLoading: Boolean): RegisterPurchaseState()
        data class Success(val response: String): RegisterPurchaseState()
        data class Error(val rawResponse: String): RegisterPurchaseState()
    }
}