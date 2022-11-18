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

    suspend fun deleteAll(purchasedProducts: List<PurchasedProduct>): Error? = tryCallNoReturnData {
        purchasedProductLocalDataSource.deleteAll(purchasedProducts.toLocalModel())
    }

    private fun PurchasedProduct.toLocalModel(): DbPurchasedProduct = DbPurchasedProduct(purchase_id = purchase_id, uuid =  uuid, categoryId = categoryId,
        description = description, price = price, image = image, amount = amount, total = total)

    private fun List<PurchasedProduct>.toLocalModel() : List<DbPurchasedProduct> = map{ it.toLocalModel() }
}