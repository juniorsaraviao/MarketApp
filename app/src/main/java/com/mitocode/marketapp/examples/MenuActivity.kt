package com.mitocode.marketapp.examples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mitocode.marketapp.R
import com.mitocode.marketapp.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    // xml: activity_menu -> activity_menuBinding
    private lateinit var binding: ActivityMenuBinding
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCourse.setOnClickListener {
            fragment = CourseFragment()
            addFragment()
        }

        binding.btnEnroll.setOnClickListener {
            fragment = EnrollFragment()
            addFragment()
        }
    }


    fun addFragment(){
        fragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        }
    }
}