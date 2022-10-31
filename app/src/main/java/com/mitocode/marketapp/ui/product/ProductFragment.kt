package com.mitocode.marketapp.ui.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mitocode.marketapp.R
import com.mitocode.marketapp.databinding.FragmentProductBinding
import com.mitocode.marketapp.ui.common.gone
import com.mitocode.marketapp.ui.common.toast
import com.mitocode.marketapp.ui.common.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment(R.layout.fragment_product) {

    private lateinit var binding: FragmentProductBinding
    private val viewModel: ProductViewModel by viewModels()
    private val safeArgs: ProductFragmentArgs by navArgs()
    //private lateinit var productAdapter: ProductAdapter
    //another way for val:
    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter() { product ->
            val directions = ProductFragmentDirections.actionProductFragmentToDetailProductFragment(product)
            Navigation.findNavController(binding.root).navigate(directions)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductBinding.bind(view)

        init()
        setupAdapter()
        collectData()
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect {
                    updateUI(it)
                }
            }
        }
    }

    private fun updateUI(state: ProductViewModel.ProductState) {
        when(state){
            ProductViewModel.ProductState.Init -> Unit
            is ProductViewModel.ProductState.Error -> requireContext().toast(state.rawResponse)
            is ProductViewModel.ProductState.IsLoading -> handlerLoading(state.isLoading)
            is ProductViewModel.ProductState.Success -> {
                productAdapter.updateList(state.products)
            }
        }
    }

    private fun handlerLoading(isLoading: Boolean) = with(binding) {
        // another way to hide
        if (isLoading) progressBar.visible()
        else progressBar.gone()
    }

    private fun setupAdapter() {
        binding.rvProducts.adapter = productAdapter
    }

    private fun init() {
        val categoryId = safeArgs.category.uuid
        viewModel.getProducts(categoryId)
    }
}