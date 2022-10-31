package com.mitocode.marketapp.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketapp.domain.Product
import com.mitocode.marketapp.usescases.RequestProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val requestProducts: RequestProducts): ViewModel() {

    private val _state = MutableStateFlow<ProductState>(ProductState.Init)
    val state: StateFlow<ProductState> = _state

    fun getProducts(categoryId: String) {

        _state.value = ProductState.IsLoading(true)

        viewModelScope.launch {
            try {

                val response = withContext(Dispatchers.IO){
                    requestProducts(categoryId)
                }

                response.collect(){
                    it.fold(
                        { error ->
                            _state.value = ProductState.Error(error.toString())
                        },
                        { products ->
                            _state.value = ProductState.Success(products)
                        }
                    )
                }

            }catch (ex: Exception){
                _state.value = ProductState.Error(ex.toString())
            }finally {
                _state.value = ProductState.IsLoading(false)
            }
        }
    }

    sealed class ProductState{
        object Init: ProductState()
        data class IsLoading(val isLoading: Boolean): ProductState()
        data class Success(val products: List<Product>): ProductState()
        data class Error(val rawResponse: String): ProductState()
    }
}