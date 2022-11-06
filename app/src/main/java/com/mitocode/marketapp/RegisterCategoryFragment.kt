package com.mitocode.marketapp

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.mitocode.marketapp.databinding.FragmentRegisterCategoryBinding
import com.mitocode.marketapp.ui.common.toast

class RegisterCategoryFragment : Fragment(R.layout.fragment_register_category) {

    private lateinit var binding: FragmentRegisterCategoryBinding

    private val loadImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        binding.imgCategory.setImageURI(uri)
    }

    private var cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
        when {
            isGranted -> startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            else -> requireContext().toast("Denied")
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result: ActivityResult ->
        if (result.resultCode == RESULT_OK){
            val data = result.data
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imgCategory.setImageBitmap(imageBitmap)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterCategoryBinding.bind(view)

        events()

    }

    // Deprecated
    /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK){

        }
    } */

    private fun events() = with(binding) {
        btnCamera.setOnClickListener {
            cameraPermission.launch(Manifest.permission.CAMERA)
        }

        btnGallery.setOnClickListener {
            loadImage.launch("image/*")
        }
    }
}