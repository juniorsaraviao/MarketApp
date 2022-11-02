package com.mitocode.marketapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mitocode.marketapp.core.BaseAdapter
import com.mitocode.marketapp.databinding.FragmentDetailProductBinding
import com.mitocode.marketapp.databinding.ItemImageProductBinding
import com.mitocode.marketapp.databinding.ItemProductBinding
import com.mitocode.marketapp.domain.Product
import com.squareup.picasso.Picasso

class DetailProductFragment : Fragment(R.layout.fragment_detail_product) {

    private lateinit var binding: FragmentDetailProductBinding
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailProductBinding.bind(view)

        setupAdapter()
        init()
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
    }
}