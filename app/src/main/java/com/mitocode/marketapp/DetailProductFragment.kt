package com.mitocode.marketapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mitocode.marketapp.databinding.FragmentDetailProductBinding
import com.squareup.picasso.Picasso

class DetailProductFragment : Fragment(R.layout.fragment_detail_product) {

    private lateinit var binding: FragmentDetailProductBinding
    private val safeArgs: DetailProductFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailProductBinding.bind(view)

        init()
    }

    private fun init() = with(binding) {
        safeArgs.product?.let { product ->
            tvDescription.text = product.description
            tvPrice.text = product.price.toString()
            tvFeatures.text = product.features

            Picasso.get().load(product.images?.get(0)).error(R.drawable.empty).into(imgDetail)
        }
    }
}