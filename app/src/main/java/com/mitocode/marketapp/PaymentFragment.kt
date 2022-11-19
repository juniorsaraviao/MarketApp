package com.mitocode.marketapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mitocode.marketapp.databinding.FragmentPaymentBinding
import com.mitocode.marketapp.ui.common.toast

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private lateinit var binding: FragmentPaymentBinding
    private val safeArgs: PaymentFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBinding.bind(view)

        init()
    }

    private fun init() {
        
    }

}