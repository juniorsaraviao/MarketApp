package com.mitocode.marketapp.domain

class PurchasedProduct(
    val uuid: String,
    val description: String,
    val price: Double,
    val image: String,
    val amount: Int,
    val total: Double
)