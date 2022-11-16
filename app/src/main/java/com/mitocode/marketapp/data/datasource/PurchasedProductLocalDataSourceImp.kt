package com.mitocode.marketapp.data.datasource

import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.local.DbPurchasedProduct
import com.mitocode.marketapp.data.local.PurchasedProductDao
import com.mitocode.marketapp.data.tryCallNoReturnData
import com.mitocode.marketapp.domain.PurchasedProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PurchasedProductLocalDataSourceImp @Inject constructor(private val purchasedProductDao: PurchasedProductDao): PurchasedProductLocalDataSource {

    override val purchasedProducts: Flow<List<PurchasedProduct>> = purchasedProductDao.getAll().map{ it.toDomainModel() }

    override suspend fun save(dbPurchasedProduct: DbPurchasedProduct): Error? = tryCallNoReturnData{
        purchasedProductDao.insertPurchasedProduct(dbPurchasedProduct)
    }

    override suspend fun update(dbPurchasedProduct: DbPurchasedProduct): Error? = tryCallNoReturnData{
        purchasedProductDao.updatePurchasedProduct(dbPurchasedProduct)
    }

    override suspend fun delete(dbPurchasedProduct: DbPurchasedProduct): Error? = tryCallNoReturnData {
        purchasedProductDao.deletePurchasedProduct(dbPurchasedProduct)
    }

    private fun List<DbPurchasedProduct>.toDomainModel() : List<PurchasedProduct> = map{ it.toDomainModel() }
    private fun DbPurchasedProduct.toDomainModel() : PurchasedProduct = PurchasedProduct(purchase_id, uuid, description, price, image, amount, total)
}