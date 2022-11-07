package com.mitocode.marketapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketapp.data.server.RegisterCategoryRequest
import com.mitocode.marketapp.usescases.RegisterCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class RegisterCategoryViewModel @Inject constructor(private val registerCategory: RegisterCategory): ViewModel() {

    private val _state = MutableStateFlow<CategoryCreateState>(CategoryCreateState.Init)
    val state: StateFlow<CategoryCreateState> = _state.asStateFlow()

    fun saveCategory(request: RegisterCategoryRequest){

        viewModelScope.launch {

            _state.value = CategoryCreateState.IsLoading(true)

            try {
                val response = withContext(Dispatchers.IO){
                    registerCategory(request)
                }
                response.collect(){
                    it.fold(
                        { error ->
                            _state.value = CategoryCreateState.Error(error.toString())
                        },
                        { response ->
                            _state.value = CategoryCreateState.Success(response.message)
                        }
                    )
                }
            }catch (e: Exception){
                _state.value = CategoryCreateState.Error(e.message.toString())
            }finally {
                _state.value = CategoryCreateState.IsLoading(false)
            }
        }

    }

    sealed class CategoryCreateState{
        object Init: CategoryCreateState()
        data class IsLoading(val isLoading: Boolean): CategoryCreateState()
        data class Success(val response: String): CategoryCreateState()
        data class Error(val rawResponse: String): CategoryCreateState()
    }
}