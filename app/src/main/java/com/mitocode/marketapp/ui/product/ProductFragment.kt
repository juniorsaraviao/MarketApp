package com.mitocode.marketapp.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mitocode.marketapp.R
import com.mitocode.marketapp.core.BaseAdapter
import com.mitocode.marketapp.databinding.FragmentProductBinding
import com.mitocode.marketapp.databinding.ItemCategoryBinding
import com.mitocode.marketapp.databinding.ItemProductBinding
import com.mitocode.marketapp.domain.Category
import com.mitocode.marketapp.domain.Product
import com.mitocode.marketapp.ui.common.gone
import com.mitocode.marketapp.ui.common.toast
import com.mitocode.marketapp.ui.common.visible
import com.squareup.picasso.Picasso
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
    /* private val productAdapter: ProductAdapter by lazy {
        ProductAdapter() { product ->
            val directions = ProductFragmentDirections.actionProductFragmentToDetailProductFragment(product)
            Navigation.findNavController(binding.root).navigate(directions)
        }
    }*/

    private val adapter: BaseAdapter<Product> = object : BaseAdapter<Product>(emptyList()){
        override fun getViewHolder(parent: ViewGroup): BaseViewHolder<Product> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
            return object: BaseViewHolder<Product>(view){

                private val binding: ItemProductBinding = ItemProductBinding.bind(itemView)

                override fun bind(entity: Product) = with(binding) {
                    tvCode.text = "Code: ${entity.code}"
                    tvDescription.text = entity.description
                    tvPrice.text = "S/. ${entity.price}"

                    Picasso.get().load(entity.images?.get(0)).error(R.drawable.empty).into(imgProduct)

                    root.setOnClickListener {
                        onItemSelected(entity)
                    }

                }

            }
        }
    }

    private fun onItemSelected(entity: Product) {
        val directions = ProductFragmentDirections.actionProductFragmentToDetailProductFragment(entity)
        Navigation.findNavController(binding.root).navigate(directions)
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
                adapter.update(state.products)
            }
        }
    }

    private fun handlerLoading(isLoading: Boolean) = with(binding) {
        // another way to hide
        if (isLoading) progressBar.visible()
        else progressBar.gone()
    }

    private fun setupAdapter() {
        binding.rvProducts.adapter = adapter
    }

    private fun init() {
        val categoryId = safeArgs.category.uuid
        viewModel.getProducts(categoryId)
    }
}