package com.mitocode.marketapp.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.mitocode.marketapp.R
import com.mitocode.marketapp.data.server.RegisterAccountRequest
import com.mitocode.marketapp.databinding.FragmentRegisterBinding
import com.mitocode.marketapp.domain.Gender
import com.mitocode.marketapp.ui.common.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
// possible to remove onCreateView if the fragment_register is added in the constructor
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private var genders: List<Gender> = listOf()
    private var gender = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        init()
        events()
        setupObservers()
    }

    private fun events() = with(binding) {
        include.imgBackHeader.setOnClickListener {
            activity?.onBackPressed()
        }

        btnRegister.setOnClickListener {
            val names = edtNames.text.toString()
            val surnames = edtSurname.text.toString()
            val email = edtEmail.text.toString()
            val phone = edtPhone.text.toString()
            val numberDocument = edtDocument.text.toString()
            val password = edtPassword.text.toString()

            if(names.isEmpty()){
                tilNames.error = getString(R.string.fragment_register_validation_names)
                return@setOnClickListener
            }
            if(surnames.isEmpty()){
                tilSurname.error = getString(R.string.fragment_register_validation_surnames)
                return@setOnClickListener
            }
            if(email.isEmpty()){
                tilEmail.error = getString(R.string.fragment_register_validation_email)
                return@setOnClickListener
            }
            if(phone.isEmpty()){
                tilPhone.error = getString(R.string.fragment_register_validation_phone)
                return@setOnClickListener
            }
            if(numberDocument.isEmpty()){
                tilDocument.error = getString(R.string.fragment_register_validation_numDoc)
                return@setOnClickListener
            }
            if(password.isEmpty()){
                tilPassword.error = getString(R.string.fragment_register_validation_password)
                return@setOnClickListener
            }

            if(!swTerms.isChecked){
                //TODO Implement popup of terms and conditions
                requireContext().toast(getString(R.string.fragment_register_validation_terms_conditions))
                return@setOnClickListener
            }

            viewModel.createAccount(RegisterAccountRequest(names, surnames, email, password, phone, gender, numberDocument))
        }

        spGender.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, l ->
            gender = genders[position].gender
        }
    }

    private fun init() = with(binding) {
        include.tvTitleHeader.text = getString(R.string.fragment_register_title)
    }

    private fun setupObservers() = with(binding){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                RegisterViewModel.RegisterAccountState.Init -> Unit
                is RegisterViewModel.RegisterAccountState.Error -> showError(state.rawResponse)
                is RegisterViewModel.RegisterAccountState.IsLoading -> showProgress(state.isLoading)
                is RegisterViewModel.RegisterAccountState.SuccessGenders -> populateGenders(state.genders)
                is RegisterViewModel.RegisterAccountState.SuccessRegister -> {
                    requireContext().toast("Bienvenido ${state.user.names} ${state.user.surnames}")
                    requireActivity().onBackPressed()
                }
            }
        }
    }

    private fun showError(error: String){
        requireContext().toast(error)
    }

    private fun showProgress(visibility: Boolean) = with(binding){
        progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    private fun populateGenders(gendersDomain: List<Gender>) = with(binding){
        spGender.setAdapter(ArrayAdapter(requireContext(), R.layout.item_spinner_gender, gendersDomain))
        genders = gendersDomain
    }

}