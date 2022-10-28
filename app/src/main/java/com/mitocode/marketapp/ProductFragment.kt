package com.mitocode.marketapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class ProductFragment : Fragment(R.layout.fragment_product) {

    private val safeArgs: ProductFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        val categoryId = safeArgs.category.uuid
    }
}