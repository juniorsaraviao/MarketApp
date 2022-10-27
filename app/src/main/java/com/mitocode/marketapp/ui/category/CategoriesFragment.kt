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
import com.mitocode.marketapp.databinding.FragmentCategoriesBinding
import com.mitocode.marketapp.ui.common.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: CategoryViewModel by viewModels()

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
                categoryAdapter.updateList(state.categories)
            }
        }
    }

    private fun showProgress(visibility: Boolean) = with(binding) {
        progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    private fun setupAdapter() = with(binding) {
        categoryAdapter = CategoryAdapter(){ category ->
            // fragment that sends data
            val directions = CategoriesFragmentDirections.actionCategoriesFragmentToProductFragment(category)
            Navigation.findNavController(root).navigate(directions)
        }
        rvCategories.adapter = categoryAdapter
    }
}