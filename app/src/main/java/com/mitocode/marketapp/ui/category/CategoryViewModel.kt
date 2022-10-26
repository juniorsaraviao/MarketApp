package com.mitocode.marketapp.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketapp.domain.Category
import com.mitocode.marketapp.usescases.RequestCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject
constructor(private val requestCategories: RequestCategories): ViewModel() {

//    Previous way to use state
//    private val _state: MutableLiveData<CategoryState> = MutableLiveData(CategoryState.Init)
//    val state: LiveData<CategoryState> = _state

    private val _state: MutableStateFlow<CategoryState> = MutableStateFlow(CategoryState.Init)
    val state: StateFlow<CategoryState> = _state

    init {
        viewModelScope.launch {

            _state.value = CategoryState.IsLoading(true)

            try{
                val response = withContext(Dispatchers.IO){
                    requestCategories()
                }

                response.collect(){
                    it.fold(
                        { error ->
                            _state.value = CategoryState.Error(error.toString())
                        },
                        { categories ->
                            _state.value = CategoryState.Success(categories)
                        }
                    )
                }
            }catch (ex: Exception){
                _state.value = CategoryState.Error(ex.message.toString())
            }finally {
                _state.value = CategoryState.IsLoading(false)
            }

        }
    }

    sealed class CategoryState(){
        object Init: CategoryState()
        data class IsLoading(val isLoading: Boolean): CategoryState()
        data class Success(val categories: List<Category>): CategoryState()
        data class Error(val rawResponse: String): CategoryState()
    }

}