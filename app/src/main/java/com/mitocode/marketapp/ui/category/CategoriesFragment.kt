package com.mitocode.marketapp.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mitocode.marketapp.R
import com.mitocode.marketapp.databinding.FragmentCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private lateinit var binding: FragmentCategoriesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoriesBinding.bind(view)
    }
}