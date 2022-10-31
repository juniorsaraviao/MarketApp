package com.mitocode.marketapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

class DetailProductFragment : Fragment(R.layout.fragment_detail_product) {

    private val safeArgs: DetailProductFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        safeArgs.product?.let { product ->  

        }
    }
}