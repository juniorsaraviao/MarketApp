package com.mitocode.marketapp.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mitocode.marketapp.R
import com.mitocode.marketapp.core.BaseAdapter
import com.mitocode.marketapp.databinding.FragmentOrdersBinding
import com.mitocode.marketapp.databinding.ItemCategoryBinding
import com.mitocode.marketapp.databinding.ItemPurchasedOrderBinding
import com.mitocode.marketapp.domain.Category
import com.mitocode.marketapp.domain.PurchasedProduct
import com.mitocode.marketapp.ui.common.toast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_purchased_order.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment : Fragment(R.layout.fragment_orders) {

    private lateinit var binding: FragmentOrdersBinding
    private val viewModel: OrdersViewModel by viewModels()

    private val adapter: BaseAdapter<PurchasedProduct> = object : BaseAdapter<PurchasedProduct>(emptyList()){
        override fun getViewHolder(parent: ViewGroup): BaseViewHolder<PurchasedProduct> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_purchased_order, parent, false)
            return object: BaseViewHolder<PurchasedProduct>(view){

                private val binding: ItemPurchasedOrderBinding = ItemPurchasedOrderBinding.bind(itemView)

                override fun bind(entity: PurchasedProduct) = with(binding) {
                    Picasso.get().load(entity.image).into(binding.imgPurchase)
                    tvPurchaseDescription.text = entity.description
                    tvAmount.text = entity.amount.toString()
                    tvTotal.text = "S/. ${entity.total}"
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOrdersBinding.bind(view)
        setupAdapter()
        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect {
                    updateUI(it)
                }
            }
        }
    }

    private fun setupAdapter() = with(binding){
        rvPurchasedOrders.adapter = adapter
    }

    private fun updateUI(state: OrdersViewModel.OrdersState) {
        when(state){
            OrdersViewModel.OrdersState.Init -> Unit
            is OrdersViewModel.OrdersState.Error -> requireContext().toast(state.rawResponse)
            is OrdersViewModel.OrdersState.IsLoading -> showProgress(state.isLoading)
            is OrdersViewModel.OrdersState.Success -> {
                adapter.update(state.purchasedProducts)
            }
        }
    }

    private fun showProgress(visibility: Boolean) = with(binding) {
        orderProgressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }
}