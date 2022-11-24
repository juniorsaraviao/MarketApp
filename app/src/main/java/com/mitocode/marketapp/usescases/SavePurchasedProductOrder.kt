package com.mitocode.marketapp.usescases

import com.mitocode.marketapp.data.repository.PurchasedProductOrderRepository
import com.mitocode.marketapp.data.server.OrderRequest
import javax.inject.Inject

class SavePurchasedProductOrder @Inject constructor(private val purchasedProductOrderRepository: PurchasedProductOrderRepository) {

    suspend operator fun invoke(request: OrderRequest) = purchasedProductOrderRepository.savePurchasedProductOrder(request)
}