package com.mitocode.marketapp

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.mitocode.marketapp.databinding.FragmentPaymentBinding
import com.mitocode.marketapp.ui.common.toast

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private lateinit var binding: FragmentPaymentBinding
    private val safeArgs: PaymentFragmentArgs by navArgs()
    private var directionType: Int = 0
    private var paymentType: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBinding.bind(view)

        init()
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
    }

}