package com.mitocode.marketapp.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.mitocode.marketapp.R
import com.mitocode.marketapp.data.GenderRemote
import com.mitocode.marketapp.databinding.FragmentRegisterBinding
import com.mitocode.marketapp.ui.common.toast
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.progressBar
import kotlinx.android.synthetic.main.header.view.*

// possible to remove onCreateView if the fragment_register is added in the constructor
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        init()
        events()
        setupObservers()
    }

    private fun events() {
        include.imgBackHeader.setOnClickListener {
            activity?.onBackPressed()
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
            }
        }
    }

    private fun showError(error: String){
        requireContext().toast(error)
    }

    private fun showProgress(visibility: Boolean) = with(binding){
        progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    private fun populateGenders(genders: List<GenderRemote>) = with(binding){
        spGender.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genders))
    }

}