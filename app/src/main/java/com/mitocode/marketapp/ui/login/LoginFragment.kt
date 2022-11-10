package com.mitocode.marketapp.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.mitocode.marketapp.BuildConfig
import com.mitocode.marketapp.MenuMainHostActivity
import com.mitocode.marketapp.R
import com.mitocode.marketapp.core.BaseFragment
import com.mitocode.marketapp.databinding.DialogVersionBinding
import com.mitocode.marketapp.databinding.FragmentLoginBinding
import com.mitocode.marketapp.ui.common.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        // Mockup
        binding.edtEmail.setText("jledesma2509@gmail.com")
        binding.edtPassword.setText("12345")

        init()
        events()
        setupObserves()
    }

    private fun init() {

        val versionApp = BuildConfig.VERSION_CODE
        val versionName = BuildConfig.VERSION_NAME

        val versionServer = 1
        if (versionApp < versionServer){
            createDialogVersion().show()
        }
    }

    private fun createDialogVersion(): AlertDialog {

        val bindingAlert = DialogVersionBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingAlert.root)

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)

        bindingAlert.btnUpdate.setOnClickListener {
            alertDialog.dismiss()
            val url = "https://play.google.com/store/apps/details?id=com.zhiliaoapp.musically"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        bindingAlert.btnLater.setOnClickListener {
            alertDialog.dismiss()
        }

        return alertDialog
    }

    private fun events() = with(binding) {
        btnSignIn.setOnClickListener {

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if(email.isEmpty()){
                tilEmail.error = getString(R.string.fragment_login_validation_email)
                return@setOnClickListener
            }

            if(password.isEmpty()){
                tilEmail.error = getString(R.string.fragment_login_validation_password)
                return@setOnClickListener
            }

            loginViewModel.auth(email, password)
        }

        tvCreateAccount.setOnClickListener {
            //Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_registerFragment)
            // replacing with BaseFragment
            navigateToAction(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setupObserves() {
        /* loginViewModel.loader.observe(viewLifecycleOwner) { condition ->
            progressBar.visibility = if (condition) View.VISIBLE else View.GONE
        }

        loginViewModel.error.observe(viewLifecycleOwner) { error ->
            requireContext().toast(error)
        }

        loginViewModel.user.observe(viewLifecycleOwner) { user ->
            requireContext().toast("Welcome ${user.names} ${user.surname}")
        } */

        loginViewModel.state.observe(viewLifecycleOwner){ state ->

            when(state){
                LoginViewModel.LoginState.Init -> Unit
                is LoginViewModel.LoginState.Error -> showError(state.rawResponse)
                is LoginViewModel.LoginState.IsLoading -> showProgress(state.isLoading)
                is LoginViewModel.LoginState.Success -> {
                    val user = state.user
                    requireContext().toast("Welcome ${user.names} ${user.surnames}")
                    val intent = Intent(requireContext(), MenuMainHostActivity::class.java)
                    startActivity(intent)
                }
            }

        }
    }

    private fun showError(error: String){
        requireContext().toast(error)
    }

    private fun showProgress(visibility: Boolean){
        progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }
}


//useRetrofit(email, password)

// USE RETROFIT + COROUTINES
// Dispatcher - Suspend functions - Scope
/* GlobalScope.launch(Dispatchers.Main) {
    try {
        // Add suspend on Api to wait for the response
        val response = withContext(Dispatchers.IO){
            Api.build().auth(LoginRequest(email, password))
        }

        if (response.isSuccessful){

            val response = response.body()
            response?.let {
                requireContext().toast(it.data!!.type)
            }

        }else{
            requireContext().toast(response.toString())
        }
    }catch (ex: Exception){
        requireContext().toast(ex.message.toString())
    }
} */



/* private fun useRetrofit(email: String, password: String) {
    // Use retrofit
    /* val request = Api.build().auth(LoginRequest(email, password))
    request.enqueue(object : Callback<WrappedResponse<UserRemote>> {

        // OnResponse - OnFailure
        override fun onResponse(
            call: Call<WrappedResponse<UserRemote>>,
            response: Response<WrappedResponse<UserRemote>>
        ) {
            if (response.isSuccessful) {

                val response = response.body()
                response?.let {
                    requireContext().toast(it.data!!.type)
                }
            } else {
                requireContext().toast(response.message())
            }
        }

        override fun onFailure(call: Call<WrappedResponse<UserRemote>>, t: Throwable) {
            requireContext().toast(t.message.toString())
        }

    }) */
} */