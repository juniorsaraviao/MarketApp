package com.mitocode.marketapp.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitocode.marketapp.R
import com.mitocode.marketapp.databinding.ItemCategoryBinding
import com.mitocode.marketapp.domain.Category
import com.squareup.picasso.Picasso

// 1. where we get the information -> List

// 3. Implement adapter methods
class CategoryAdapter constructor(
    private var categories: List<Category> = listOf(),
    val itemClicked: (Category)->Unit): RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    // 2. Define intern ViewHolder class
    // we need data to populate and where the data will be displayed (view)
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding: ItemCategoryBinding = ItemCategoryBinding.bind(itemView)

        fun bind(category: Category){
            // Load with Picasso
            Picasso.get().load(category.cover)
                //.error(R.drawable.splash_footer)
                .into(binding.imgCategory)

            binding.root.setOnClickListener {
                itemClicked(category)
            }
        }
    }

    fun updateList(categories: List<Category>){
        this.categories = categories
        notifyDataSetChanged()
    }

    // Inflate the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView:View = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(itemView)
    }

    // Iterate each element
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    // how many elements the list has
    override fun getItemCount(): Int {
        return categories.size
    }
}