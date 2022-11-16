package com.mitocode.marketapp.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketapp.domain.Category
import com.mitocode.marketapp.domain.PurchasedProduct
import com.mitocode.marketapp.ui.category.CategoryViewModel
import com.mitocode.marketapp.usescases.DeletePurchasedProduct
import com.mitocode.marketapp.usescases.GetPurchasedProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject
constructor(private val getPurchasedProducts: GetPurchasedProducts,
private val deletePurchasedProduct: DeletePurchasedProduct): ViewModel() {

    private val _state: MutableStateFlow<OrdersState> = MutableStateFlow(OrdersState.Init)
    val state: StateFlow<OrdersState> = _state

    fun loadPurchasedProducts(){
        viewModelScope.launch {
            getPurchasedProducts()
                .catch { error ->
                    _state.value = OrdersState.Error(error.toString())
                }
                .collect{ purchasedProducts ->
                    _state.value = OrdersState.Success(purchasedProducts)
                    _state.value = OrdersState.Amount(purchasedProducts.sumOf { it.total })
                }
        }
    }

    fun deleteProduct(purchasedProduct: PurchasedProduct){
        viewModelScope.launch {
            _state.value = OrdersState.IsLoading(true)
            deletePurchasedProduct(purchasedProduct)
            loadPurchasedProducts()
            _state.value = OrdersState.IsLoading(false)
        }
    }

    sealed class OrdersState(){
        object Init: OrdersState()
        data class IsLoading(val isLoading: Boolean): OrdersState()
        data class Success(val purchasedProducts: List<PurchasedProduct>): OrdersState()
        data class Error(val rawResponse: String): OrdersState()
        data class Amount(val amount: Double): OrdersState()
    }
}