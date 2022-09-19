package com.mitocode.marketapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mitocode.marketapp.R
import com.mitocode.marketapp.data.Api
import com.mitocode.marketapp.data.LoginRequest
import com.mitocode.marketapp.data.UserRemote
import com.mitocode.marketapp.data.WrappedResponse
import com.mitocode.marketapp.databinding.FragmentLoginBinding
import com.mitocode.marketapp.ui.common.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

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

        binding.btnSignIn.setOnClickListener {

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            // Use retrofit
            val request = Api.build().auth(LoginRequest(email, password))
            request.enqueue(object : Callback<WrappedResponse<UserRemote>>{

                // OnResponse - OnFailure
                override fun onResponse(call: Call<WrappedResponse<UserRemote>>, response: Response<WrappedResponse<UserRemote>>) {
                    if (response.isSuccessful){

                        val response = response.body()
                        response?.let {
                            requireContext().toast(it.data!!.type)
                        }
                    }else{
                        requireContext().toast(response.message())
                    }
                }

                override fun onFailure(call: Call<WrappedResponse<UserRemote>>, t: Throwable) {
                    requireContext().toast(t.message.toString())
                }

            })

        }
    }
}