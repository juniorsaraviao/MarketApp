package com.mitocode.marketapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DestinationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination)

        // Get data
        val bundle = intent.extras

        // Scope function - LET
        bundle?.let {
            val name = bundle.getString("KEY_NAME")
            val doc = bundle.getString("KEY_DOC")
            val typeDoc = bundle.getString("TYPE_DOC")

            println("Names: $name - Doc Number: $doc - Type Doc: $typeDoc")
        }

        // Common programming code
//        if (bundle != null){
//
//        }
    }
}