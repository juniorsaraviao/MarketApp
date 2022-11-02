package com.mitocode.marketapp.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.mitocode.marketapp.R
import com.mitocode.marketapp.core.BaseAdapter
import com.mitocode.marketapp.core.BaseFragment
import com.mitocode.marketapp.databinding.FragmentCategoriesBinding
import com.mitocode.marketapp.databinding.ItemCategoryBinding
import com.mitocode.marketapp.domain.Category
import com.mitocode.marketapp.ui.common.toast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : BaseFragment(R.layout.fragment_categories) {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: CategoryViewModel by viewModels()
    // anonymous class
    private val adapter: BaseAdapter<Category> = object : BaseAdapter<Category>(emptyList()){
        override fun getViewHolder(parent: ViewGroup): BaseViewHolder<Category> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
            return object: BaseViewHolder<Category>(view){

                private val binding: ItemCategoryBinding = ItemCategoryBinding.bind(itemView)

                override fun bind(entity: Category) {
                    Picasso.get().load(entity.cover).into(binding.imgCategory)

                    binding.root.setOnClickListener {
                        onItemSelected(entity)
                    }
                }

            }
        }
    }

    private fun onItemSelected(entity: Category) {
        val directions = CategoriesFragmentDirections.actionCategoriesFragmentToProductFragment(entity)
        navigateToDirections(directions)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoriesBinding.bind(view)

        setupAdapter()
        setupObservers()
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

    private fun updateUI(state: CategoryViewModel.CategoryState) {
        when(state){
            CategoryViewModel.CategoryState.Init -> Unit
            is CategoryViewModel.CategoryState.Error -> requireContext().toast(state.rawResponse)
            is CategoryViewModel.CategoryState.IsLoading -> showProgress(state.isLoading)
            is CategoryViewModel.CategoryState.Success -> {
                adapter.update(state.categories)
            }
        }
    }

    private fun showProgress(visibility: Boolean) = with(binding) {
        progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    private fun setupAdapter() = with(binding) {
        /* categoryAdapter = CategoryAdapter(){ category ->
            // fragment that sends data
            val directions = CategoriesFragmentDirections.actionCategoriesFragmentToProductFragment(category)
            // Navigation.findNavController(root).navigate(directions)
            // replacing with BaseFragment
            navigateToDirections(directions)
        } */
        rvCategories.adapter = adapter
    }
}