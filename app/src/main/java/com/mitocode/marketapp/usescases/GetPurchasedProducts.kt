package com.mitocode.marketapp.usescases

import com.mitocode.marketapp.data.repository.PurchasedProductRepository
import javax.inject.Inject

class GetPurchasedProducts @Inject
constructor(private val purchasedProductRepository: PurchasedProductRepository) {
    operator fun invoke() = purchasedProductRepository.purchasedProducts
}