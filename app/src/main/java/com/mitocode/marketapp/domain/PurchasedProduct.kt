package com.mitocode.marketapp.domain

class PurchasedProduct(
    val purchase_id: Int = 0,
    val uuid: String,
    val categoryId: String,
    val description: String,
    val price: Double,
    val image: String,
    var amount: Int,
    var total: Double
)