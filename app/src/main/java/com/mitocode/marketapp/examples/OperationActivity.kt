package com.mitocode.marketapp.examples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mitocode.marketapp.R
import com.mitocode.marketapp.databinding.ActivityOperationBinding
import com.mitocode.marketapp.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.activity_operation.*

class OperationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOperationBinding

    // Subscribe viewModel ktx
    private val viewModel: OperationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOperationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_operation)

        viewModel.number.observe(this){ number ->
            tvNumber.text = "$number"
        }

        binding.btnPlus.setOnClickListener {
            val number = tvNumber.text.toString().toInt() + 1
            viewModel.setNumber((number))
            //tvNumber.text = "$number"
        }

        binding.btnSubstract.setOnClickListener {
            val number = tvNumber.text.toString().toInt() - 1
            viewModel.setNumber((number))
            //tvNumber.text = "$number"
        }
    }
}