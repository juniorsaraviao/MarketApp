package com.mitocode.marketapp.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketapp.domain.Category
import com.mitocode.marketapp.domain.PurchasedProduct
import com.mitocode.marketapp.ui.category.CategoryViewModel
import com.mitocode.marketapp.usescases.GetPurchasedProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject
constructor(getPurchasedProducts: GetPurchasedProducts): ViewModel() {

    private val _state: MutableStateFlow<OrdersState> = MutableStateFlow(OrdersState.Init)
    val state: StateFlow<OrdersState> = _state

    init {

        viewModelScope.launch {
            getPurchasedProducts()
                .catch { error ->
                    _state.value = OrdersState.Error(error.toString())
                }
                .collect{ purchasedProducts ->
                    _state.value = OrdersState.Success(purchasedProducts)
                }
        }
    }

    sealed class OrdersState(){
        object Init: OrdersState()
        data class IsLoading(val isLoading: Boolean): OrdersState()
        data class Success(val purchasedProducts: List<PurchasedProduct>): OrdersState()
        data class Error(val rawResponse: String): OrdersState()
    }
}