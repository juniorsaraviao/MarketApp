package com.mitocode.marketapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketapp.data.server.OrderRequest
import com.mitocode.marketapp.domain.PurchasedProduct
import com.mitocode.marketapp.ui.order.OrdersViewModel
import com.mitocode.marketapp.ui.product.ProductViewModel
import com.mitocode.marketapp.usescases.DeleteAllPurchasedProducts
import com.mitocode.marketapp.usescases.RegisterProduct
import com.mitocode.marketapp.usescases.SavePurchasedProductOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val savePurchasedProductOrder: SavePurchasedProductOrder,
    private val deleteAllPurchasedProducts: DeleteAllPurchasedProducts) : ViewModel() {

    private val _state = MutableStateFlow<RegisterPurchasedPurchasedOrder>(RegisterPurchasedPurchasedOrder.Init)
    val state: StateFlow<RegisterPurchasedPurchasedOrder> = _state.asStateFlow()

    fun savePurchasedOrder(request: OrderRequest){
        _state.value = RegisterPurchasedPurchasedOrder.IsLoading(true)

        viewModelScope.launch {
            try {

                val response = withContext(Dispatchers.IO){
                    savePurchasedProductOrder(request)
                }

                response.collect(){
                    it.fold(
                        { error ->
                            _state.value = RegisterPurchasedPurchasedOrder.Error(error.toString())
                        },
                        { res ->
                            if(res.success){
                                _state.value = RegisterPurchasedPurchasedOrder.Success(res.data!!)
                            }else{
                                _state.value = RegisterPurchasedPurchasedOrder.Error(res.message)
                            }
                        }
                    )
                }

            }catch (ex: Exception){
                _state.value = RegisterPurchasedPurchasedOrder.Error(ex.toString())
            }finally {
                _state.value = RegisterPurchasedPurchasedOrder.IsLoading(false)
            }
        }
    }

    fun deleteAllProducts(purchasedProducts: List<PurchasedProduct>){
        viewModelScope.launch {
            try{
                _state.value = RegisterPurchasedPurchasedOrder.IsLoading(true)
                deleteAllPurchasedProducts(purchasedProducts)
            }catch (ex: Exception){
                _state.value = RegisterPurchasedPurchasedOrder.Error(ex.toString())
            }finally {
                _state.value = RegisterPurchasedPurchasedOrder.IsLoading(false)
            }
        }
    }

    sealed class RegisterPurchasedPurchasedOrder {
        object Init: RegisterPurchasedPurchasedOrder()
        data class IsLoading(val isLoading: Boolean): RegisterPurchasedPurchasedOrder()
        data class Success(val response: String): RegisterPurchasedPurchasedOrder()
        data class Error(val rawResponse: String): RegisterPurchasedPurchasedOrder()
    }
}