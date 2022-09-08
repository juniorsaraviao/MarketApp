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
            val name = it.getString("KEY_NAME") ?: "UNKNOWN"
            val doc = it.getString("KEY_DOC") ?: "UNKNOWN"
            val typeDoc = it.getString("TYPE_DOC") ?: "UNKNOWN"

            println("Names: $name - Doc Number: $doc - Type Doc: $typeDoc")
        } ?: run {
            // execute if bundle is null
        }

        // Common programming code
//        if (bundle != null){
//
//        }
    }
}