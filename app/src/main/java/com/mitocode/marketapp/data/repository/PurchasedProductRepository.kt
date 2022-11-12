package com.mitocode.marketapp.data.repository

import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.datasource.PurchasedProductLocalDataSource
import com.mitocode.marketapp.data.local.DbPurchasedProduct
import com.mitocode.marketapp.data.tryCallNoReturnData
import com.mitocode.marketapp.domain.PurchasedProduct
import javax.inject.Inject

class PurchasedProductRepository
@Inject constructor(
    private val purchasedProductLocalDataSource: PurchasedProductLocalDataSource)
{
    val purchasedProducts = purchasedProductLocalDataSource.purchasedProducts

    suspend fun updatePurchaseProduct(purchasedProduct: PurchasedProduct): Error? = tryCallNoReturnData {
        purchasedProductLocalDataSource.update(purchasedProduct.toLocalModel())
    }

    suspend fun savePurchaseProduct(purchasedProduct: PurchasedProduct): Error? = tryCallNoReturnData {
        purchasedProductLocalDataSource.save(purchasedProduct.toLocalModel())
    }

    suspend fun deletePurchaseProduct(purchasedProduct: PurchasedProduct): Error? = tryCallNoReturnData {
        purchasedProductLocalDataSource.delete(purchasedProduct.toLocalModel())
    }

    private fun PurchasedProduct.toLocalModel(): DbPurchasedProduct = DbPurchasedProduct(uuid =  uuid, description = description, price = price,
        image = image, amount = amount, total = price*amount)
}