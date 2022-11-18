package com.mitocode.marketapp.data.datasource

import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.local.DbPurchasedProduct
import com.mitocode.marketapp.domain.PurchasedProduct
import kotlinx.coroutines.flow.Flow

interface PurchasedProductLocalDataSource {
    val purchasedProducts: Flow<List<PurchasedProduct>>

    suspend fun save(dbPurchasedProduct: DbPurchasedProduct): Error?

    suspend fun update(dbPurchasedProduct: DbPurchasedProduct): Error?

    suspend fun delete(dbPurchasedProduct: DbPurchasedProduct): Error?

    suspend fun deleteAll(dbPurchasedProduct: List<DbPurchasedProduct>): Error?
}