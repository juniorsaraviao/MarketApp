package com.mitocode.marketapp.domain

import java.io.Serializable

class Product(
    val uuid: String,
    val description: String,
    val code: String,
    val features: String,
    val price: Double,
    val stock: Int,
    val images: List<String>? = null,
    val amount: Int
): Serializable