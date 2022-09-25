package com.mitocode.marketapp.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mitocode.marketapp.R
import com.mitocode.marketapp.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.header.view.*

// possible to remove onCreateView if the fragment_register is added in the constructor
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        init()
        events()
    }

    private fun events() {
        include.imgBackHeader.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun init() = with(binding) {
        include.tvTitleHeader.text = getString(R.string.fragment_register_title)
    }

}