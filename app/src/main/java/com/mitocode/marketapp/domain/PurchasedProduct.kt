package com.mitocode.marketapp.domain

import java.io.Serializable

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

class PurchasedProductList(
    val purchasedProductList: List<PurchasedProduct>
): Serializable