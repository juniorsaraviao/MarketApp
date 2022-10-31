package com.mitocode.marketapp.ui.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitocode.marketapp.R
import com.mitocode.marketapp.databinding.ItemProductBinding
import com.mitocode.marketapp.domain.Product
import com.squareup.picasso.Picasso

// don't need to add 'constructor'
class ProductAdapter (var products: List<Product> = listOf())
    : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    // intern class
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val binding: ItemProductBinding = ItemProductBinding.bind(itemView)

        fun bind(product: Product) = with(binding){
            tvCode.text = "Code: ${product.code}"
            tvDescription.text = product.description
            tvPrice.text = "S/. ${product.price}"

            Picasso.get().load(product.images?.get(0)).error(R.drawable.empty).into(imgProduct)
        }
    }

    fun updateList(products: List<Product>){
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView:View = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }
}