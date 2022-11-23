package com.mitocode.marketapp

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.mitocode.marketapp.data.server.OrderRequest
import com.mitocode.marketapp.databinding.FragmentPaymentBinding
import com.mitocode.marketapp.ui.common.gone
import com.mitocode.marketapp.ui.common.toast
import com.mitocode.marketapp.ui.common.visible
import kotlinx.coroutines.launch

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private lateinit var binding: FragmentPaymentBinding
    private val viewModel: PaymentViewModel by viewModels()
    private val safeArgs: PaymentFragmentArgs by navArgs()
    private var directionType: Int = 0
    private var paymentType: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBinding.bind(view)

        init()
        setupObservables()
    }

    private fun init() = with(binding) {
        imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        imgDirection.setOnClickListener {
            if(constraintDirection.visibility == View.GONE) {
                imgDirection.animate().rotation(-180f).setDuration(500).start()
            }else{
                imgDirection.animate().rotation(0f).setDuration(500).start()
            }
            constraintDirection.visibility = if(constraintDirection.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        imgDateTime.setOnClickListener {
            if(constraintDateTime.visibility == View.GONE) {
                imgDateTime.animate().rotation(-180f).setDuration(500).start()
            }else{
                imgDateTime.animate().rotation(0f).setDuration(500).start()
            }
            constraintDateTime.visibility = if(constraintDateTime.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        imgPaymentType.setOnClickListener {
            if(constraintPaymentType.visibility == View.GONE) {
                imgPaymentType.animate().rotation(-180f).setDuration(500).start()
            }else{
                imgPaymentType.animate().rotation(0f).setDuration(500).start()
            }
            constraintPaymentType.visibility = if(constraintPaymentType.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        rgDirection.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                rbHome.id -> directionType = 1
                rbOffice.id -> directionType = 2
                rbOther.id -> directionType = 3
            }
        }

        rgPaymentType.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                rbYape.id -> paymentType = 1
                rbPlin.id -> paymentType = 2
                rbCash.id -> paymentType = 3
            }
        }

        tvPaymentTotalAmount.text = "S/. ${safeArgs.purchasedProducts.purchasedProductList.sumOf { it.total }}"

        btnPay.setOnClickListener {
            if(directionType == 0 && etDirection.text!!.isEmpty() && etReference.text!!.isEmpty() && etDistrict.text!!.isEmpty() &&
                    etDate.text!!.isEmpty() && etTime.text!!.isEmpty() && paymentType == 0 && etTotalAmount.text!!.isEmpty()){
                showToast()
                return@setOnClickListener
            }

            //viewModel.savePurchasedOrder(OrderRequest())
        }

    }

    private fun setupObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{ state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: PaymentViewModel.RegisterPurchasedPurchasedOrder) {
        when(state){
            PaymentViewModel.RegisterPurchasedPurchasedOrder.Init -> Unit
            is PaymentViewModel.RegisterPurchasedPurchasedOrder.Error -> requireContext().toast(state.rawResponse)
            is PaymentViewModel.RegisterPurchasedPurchasedOrder.IsLoading -> handlerLoading(state.isLoading)
            is PaymentViewModel.RegisterPurchasedPurchasedOrder.Success -> {
                requireContext().toast(state.response)
                requireActivity().onBackPressed()
            }
        }
    }

    private fun handlerLoading(isLoading: Boolean) = with(binding){
        if (isLoading) paymentProgressBar.visible()
        else paymentProgressBar.gone()
    }

    private fun showToast(){
        requireContext().toast("Ingresa todo los campos requeridos")
    }

}