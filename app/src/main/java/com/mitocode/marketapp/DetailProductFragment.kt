package com.mitocode.marketapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.mitocode.marketapp.core.BaseAdapter
import com.mitocode.marketapp.databinding.FragmentDetailProductBinding
import com.mitocode.marketapp.databinding.ItemImageProductBinding
import com.mitocode.marketapp.databinding.ItemProductBinding
import com.mitocode.marketapp.domain.Product
import com.mitocode.marketapp.domain.PurchasedProduct
import com.mitocode.marketapp.ui.common.gone
import com.mitocode.marketapp.ui.common.toast
import com.mitocode.marketapp.ui.common.visible
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_operation.*
import kotlinx.android.synthetic.main.fragment_detail_product.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailProductFragment : Fragment(R.layout.fragment_detail_product) {

    private lateinit var binding: FragmentDetailProductBinding
    private val detailProductViewModel: DetailProductViewModel by viewModels()
    private val safeArgs: DetailProductFragmentArgs by navArgs()

    private val adapter: BaseAdapter<String> = object : BaseAdapter<String>(emptyList()){
        override fun getViewHolder(parent: ViewGroup): BaseViewHolder<String> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_product, parent, false)
            return object: BaseViewHolder<String>(view){
                private val binding: ItemImageProductBinding = ItemImageProductBinding.bind(itemView)
                override fun bind(entity: String) = with(binding) {
                    Picasso.get().load(entity).error(R.drawable.empty).into(imgProduct)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailProductViewModel.number.observe(this){ number ->
            tvQuantity.text = "$number"
            if (number == 0){
                btnMinusItem.isEnabled = false
                btnMinusItem.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray_color))
                btnAdd.isEnabled = false
            }else{
                btnMinusItem.isEnabled = true
                btnMinusItem.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_color))
                btnAdd.isEnabled = true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailProductBinding.bind(view)

        setupAdapter()
        init()
        setupObservables()
    }

    private fun setupAdapter() = with(binding) {
        rvProductsDetail.adapter = adapter
    }

    private fun init() = with(binding) {

        safeArgs.product?.let { product ->
            tvDescription.text = product.description
            tvPrice.text = "S/. ${product.price}"
            tvFeatures.text = product.features

            Picasso.get().load(product.images?.get(0)).error(R.drawable.empty).into(imgDetail)
            adapter.update(product.images!!)
        }

        btnMinusItem.setOnClickListener {
            detailProductViewModel.subtractItem()
        }

        btnPlusItem.setOnClickListener {
            detailProductViewModel.addItem()
        }

        btnAdd.setOnClickListener {
            safeArgs.product?.let { product ->
                tvDescription.text = product.description
                tvPrice.text = "S/. ${product.price}"
                tvFeatures.text = product.features

                Picasso.get().load(product.images?.get(0)).error(R.drawable.empty).into(imgDetail)
                adapter.update(product.images!!)

                detailProductViewModel.savePurchase(PurchasedProduct(product.uuid,
                    product.description, product.price, product.images[0], tvQuantity.text.toString().toInt()))
            }
        }
    }

    private fun setupObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                detailProductViewModel.state.collect{ state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: DetailProductViewModel.RegisterPurchaseState) {
        when(state){
            DetailProductViewModel.RegisterPurchaseState.Init -> Unit
            is DetailProductViewModel.RegisterPurchaseState.Error -> requireContext().toast(state.rawResponse)
            is DetailProductViewModel.RegisterPurchaseState.IsLoading -> handlerLoading(state.isLoading)
            is DetailProductViewModel.RegisterPurchaseState.Success -> {
                requireContext().toast(state.response)
                requireActivity().onBackPressed()
            }
        }
    }

    private fun handlerLoading(isLoading: Boolean) = with(binding){
        if (isLoading) progressBarDetailProduct.visible()
        else progressBarDetailProduct.gone()
    }
}