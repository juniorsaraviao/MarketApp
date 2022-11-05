package com.mitocode.marketapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.mitocode.marketapp.databinding.FragmentRegisterCategoryBinding

class RegisterCategoryFragment : Fragment(R.layout.fragment_register_category) {

    private lateinit var binding: FragmentRegisterCategoryBinding

    val loadImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        binding.imgCategory.setImageURI(uri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterCategoryBinding.bind(view)

        events()

    }

    private fun events() = with(binding) {
        btnCamera.setOnClickListener {

        }

        btnGallery.setOnClickListener {
            loadImage.launch("image/*")
        }
    }
}