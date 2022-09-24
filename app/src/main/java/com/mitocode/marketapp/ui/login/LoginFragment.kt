package com.mitocode.marketapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mitocode.marketapp.R
import com.mitocode.marketapp.data.Api
import com.mitocode.marketapp.data.LoginRequest
import com.mitocode.marketapp.databinding.FragmentLoginBinding
import com.mitocode.marketapp.ui.common.toast
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        events()

        setupObserves()
    }

    private fun setupObserves() {
        loginViewModel.loader.observe(viewLifecycleOwner) { condition ->
            progressBar.visibility = if (condition) View.VISIBLE else View.GONE
        }

        loginViewModel.error.observe(viewLifecycleOwner) { error ->
            requireContext().toast(error)
        }

        loginViewModel.user.observe(viewLifecycleOwner) { user ->
            requireContext().toast("Welcome ${user.names} ${user.surname}")
        }
    }

    private fun events() {
        binding.btnSignIn.setOnClickListener {

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            loginViewModel.auth(email, password)
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



    private fun useRetrofit(email: String, password: String) {
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
    }
}