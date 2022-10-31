package com.mitocode.marketapp.ui.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mitocode.marketapp.R
import com.mitocode.marketapp.databinding.FragmentProductBinding

class ProductFragment : Fragment(R.layout.fragment_product) {

    private lateinit var binding: FragmentProductBinding
    private val viewModel: ProductViewModel by viewModels()
    private val safeArgs: ProductFragmentArgs by navArgs()
    //private lateinit var productAdapter: ProductAdapter
    //another way for val:
    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductBinding.bind(view)

        init()
        setupAdapter()
    }

    private fun setupAdapter() {
        binding.rvProducts.adapter = productAdapter
    }

    private fun init() {
        val categoryId = safeArgs.category.uuid
        viewModel.getProducts(categoryId)
    }
}