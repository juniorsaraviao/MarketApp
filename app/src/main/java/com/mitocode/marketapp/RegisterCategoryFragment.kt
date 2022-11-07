package com.mitocode.marketapp

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mitocode.marketapp.data.server.RegisterCategoryRequest
import com.mitocode.marketapp.databinding.FragmentRegisterCategoryBinding
import com.mitocode.marketapp.ui.common.gone
import com.mitocode.marketapp.ui.common.toast
import com.mitocode.marketapp.ui.common.visible
import com.mitocode.marketapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class RegisterCategoryFragment : Fragment(R.layout.fragment_register_category) {

    private lateinit var binding: FragmentRegisterCategoryBinding
    private val viewModel: RegisterCategoryViewModel by viewModels()
    private var imageBase64 = ""

    private val loadImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        val inputStream = uri?.let {
            requireContext().contentResolver.openInputStream(it)
        }
        val imageBitmap = BitmapFactory.decodeStream(inputStream)
        binding.imgCategory.setImageURI(uri)

        GlobalScope.launch {
            withContext(Dispatchers.IO){
                converterBase64(imageBitmap)
            }
        }
    }

    private fun converterBase64(imageBitmap: Bitmap){
        val byteArrayOutputStream = ByteArrayOutputStream()
        imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        imageBase64 = Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    /*private val cropResultContracts = object: ActivityResultContract<Any?, Uri?>(){
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            TODO("Not yet implemented")
        }

    }*/

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

            GlobalScope.launch {
                withContext(Dispatchers.IO){
                    converterBase64(imageBitmap)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterCategoryBinding.bind(view)

        events()
        setupObservables()

    }

    private fun setupObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{ state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: RegisterCategoryViewModel.CategoryCreateState) {
        when(state){
            RegisterCategoryViewModel.CategoryCreateState.Init -> Unit
            is RegisterCategoryViewModel.CategoryCreateState.Error -> requireContext().toast(state.rawResponse)
            is RegisterCategoryViewModel.CategoryCreateState.IsLoading -> handlerLoading(state.isLoading)
            is RegisterCategoryViewModel.CategoryCreateState.Success -> requireContext().toast(state.response)
        }
    }

    private fun handlerLoading(isLoading: Boolean) = with(binding){
        // another way to hide
        if (isLoading) progress.visible()
        else progress.gone()
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

        btnSave.setOnClickListener {
            val name = edtNameCategory.text.toString()
            viewModel.saveCategory(RegisterCategoryRequest(name, "${Constants.FORMAT_BASE_64}${imageBase64}"))
        }
    }
}