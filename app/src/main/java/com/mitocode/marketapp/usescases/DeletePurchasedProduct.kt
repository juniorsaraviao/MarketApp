package com.mitocode.marketapp.usescases

import com.mitocode.marketapp.data.repository.PurchasedProductRepository
import javax.inject.Inject

class DeletePurchasedProduct @Inject
constructor(private val purchasedProductRepository: PurchasedProductRepository) {
    //suspend operator fun invoke(purchase) = purchasedProductRepository.purchasedProducts
}