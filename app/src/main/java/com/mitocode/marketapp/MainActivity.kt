package com.mitocode.marketapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Variables mutable - immutable
        var name = "Junior"
        var age = 32
        var price = 20.5
        var condition = true

        val pi = 3.1416

        //        Inject views: FindView - Kotlin extension - ViewBinding

        // FindView
        // val btnSend: Button = findViewById(R.id.btnSend)

        // Kotlin extension
        btnSend.setOnClickListener {
            // Get data
            val name = edtName.text.toString()
            val doc = edtDocNumber.text.toString()
            val type = if (rbDni.isChecked) "DNI" else "Carnet"

            // validate
            if (name.isEmpty()){
                Toast.makeText(this, getString(R.string.warning_name), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (doc.isEmpty()){
                Toast.makeText(this, getString(R.string.warning_document), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Scope functions - LET, APPLY, RUN, WITH - reduce code

            // Save data - BUNDLE
            val bundle = Bundle().apply{
                putString("KEY_NAME", name)
                putString("KEY_DOC", doc)
                putString("TYPE_DOC", type)
            }

            // Navigation - INTENT
            val intent = Intent(this, DestinationActivity::class.java).apply {
                putExtras(bundle)
            }
            val sum = 5.plus2(3)
            val sum2 = 6 mas 5

            val sum3 = operatorFun(5, 6){ x,y ->
                x + y
            }

            hello("Junior") {
                println(it)
            }
            startActivity(intent)
        }
    }

    // Extensions
    // every int adds 2
    private fun Int.plus2(number: Int){
        this + number
    }

    // Infix - only one parameter
    infix fun Int.mas(number: Int) = this + number

    // Lambdas // (Parameter) -> Return
    fun operatorFun(x:Int, y: Int, myFun: (Int, Int) -> Int): Int{
        return myFun(x, y)
    }

    fun hello(name: String, hi: (String) -> Unit){
        hi(name)
    }
}