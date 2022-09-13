package com.mitocode.marketapp.examples

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.toast(message: String){
   Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

infix fun View.click(click: () -> Unit){
    setOnClickListener{ click }
}