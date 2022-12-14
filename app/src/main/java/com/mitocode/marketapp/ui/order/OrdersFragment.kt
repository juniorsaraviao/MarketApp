package com.mitocode.marketapp.ui.order

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.mitocode.marketapp.R
import com.mitocode.marketapp.core.BaseAdapter
import com.mitocode.marketapp.databinding.DialogUpdatePurchaseBinding
import com.mitocode.marketapp.databinding.DialogVersionBinding
import com.mitocode.marketapp.databinding.FragmentOrdersBinding
import com.mitocode.marketapp.databinding.ItemPurchasedOrderBinding
import com.mitocode.marketapp.domain.Product
import com.mitocode.marketapp.domain.PurchasedProduct
import com.mitocode.marketapp.domain.PurchasedProductList
import com.mitocode.marketapp.ui.common.toast
import com.mitocode.marketapp.ui.product.ProductFragmentDirections
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_orders.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment : Fragment(R.layout.fragment_orders) {

    private lateinit var binding: FragmentOrdersBinding
    private val viewModel: OrdersViewModel by viewModels()
    private val formatNumber = "%,.2f"
    private var purchasedProductsList: List<PurchasedProduct> = listOf()

    private val adapter: BaseAdapter<PurchasedProduct> = object : BaseAdapter<PurchasedProduct>(emptyList()){
        override fun getViewHolder(parent: ViewGroup): BaseViewHolder<PurchasedProduct> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_purchased_order, parent, false)
            return object: BaseViewHolder<PurchasedProduct>(view){

                private val binding: ItemPurchasedOrderBinding = ItemPurchasedOrderBinding.bind(itemView)

                override fun bind(entity: PurchasedProduct) = with(binding) {
                    Picasso.get().load(entity.image).error(R.drawable.empty).into(binding.imgPurchase)
                    tvPurchaseDescription.text = entity.description
                    tvAmount.text = entity.amount.toString()
                    tvTotal.text = "S/. ${formatNumber.format(entity.total)}"

                    imgDelete.setOnClickListener {
                        viewModel.deleteProduct(entity)
                    }

                    imgEdit.setOnClickListener {
                        updatePurchasedProductDialog(entity).show()
                    }
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOrdersBinding.bind(view)
        init()
        setupAdapter()
        setupObservers()
    }

    private fun init() {
        viewModel.loadPurchasedProducts()
        binding.imgDeleteAll.setOnClickListener {
            if(!purchasedProductsList.any()) {
                requireContext().toast("No tienes productos para eliminar")
                return@setOnClickListener
            }
            deleteAllProductsDialog().show()
        }
        binding.btnCheckIn.setOnClickListener {
            onItemSelected(purchasedProductsList)
        }
    }

    private fun onItemSelected(entity: List<PurchasedProduct>) {
        val directions = OrdersFragmentDirections.actionOrdersFragmentToPaymentFragment(PurchasedProductList(entity))
        Navigation.findNavController(binding.root).navigate(directions)
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
                checkPurchasedProduct(state)
            }
            is OrdersViewModel.OrdersState.Amount -> {
                binding.tvTotalAmount.text = "S/. ${formatNumber.format(state.amount)}"
            }
        }
    }

    private fun checkPurchasedProduct(state: OrdersViewModel.OrdersState.Success) {
        if (state.purchasedProducts.any()) {
            purchasedProductsList = state.purchasedProducts
            adapter.update(state.purchasedProducts)
            binding.btnCheckIn.isEnabled = true
            binding.rvPurchasedOrders.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE

        } else {
            binding.btnCheckIn.isEnabled = false
            binding.rvPurchasedOrders.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        }
    }

    private fun showProgress(visibility: Boolean) = with(binding) {
        orderProgressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    private fun updatePurchasedProductDialog(purchasedProduct: PurchasedProduct): AlertDialog {

        val bindingAlert = DialogUpdatePurchaseBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingAlert.root)

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)

        bindingAlert.edtAmount.setText(purchasedProduct.amount.toString())

        bindingAlert.btnUpdate.setOnClickListener {
            val amount = bindingAlert.edtAmount.text.toString().toInt()
            if (amount > 0){
                purchasedProduct.amount = amount
                purchasedProduct.total = amount*purchasedProduct.price
                viewModel.updateProduct(purchasedProduct)
                alertDialog.dismiss()
            }else{
                requireContext().toast("Cantidad ingresada no valida")
            }

        }

        bindingAlert.btnClose.setOnClickListener {
            alertDialog.dismiss()
        }

        return alertDialog
    }

    private fun deleteAllProductsDialog(): AlertDialog {

        val bindingAlert = DialogVersionBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingAlert.root)

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)

        bindingAlert.btnUpdate.text = "Eliminar"
        bindingAlert.btnLater.text = "Cancelar"
        bindingAlert.tvMessage.text = "??Est??s seguro de eliminar todos los productos de la lista?"

        bindingAlert.btnUpdate.setOnClickListener {
            viewModel.deleteAllProducts(purchasedProductsList)
            alertDialog.dismiss()
        }

        bindingAlert.btnLater.setOnClickListener {
            alertDialog.dismiss()
        }

        return alertDialog
    }
}