package com.mitocode.marketapp.usescases

import com.mitocode.marketapp.data.repository.PurchasedProductRepository
import com.mitocode.marketapp.domain.PurchasedProduct
import javax.inject.Inject

class RegisterProduct @Inject constructor(private val purchasedProductRepository: PurchasedProductRepository) {

    suspend operator fun invoke(purchasedProduct: PurchasedProduct) = purchasedProductRepository.savePurchaseProduct(purchasedProduct)
}