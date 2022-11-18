package com.mitocode.marketapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchasedProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPurchasedProduct(dbProduct: DbPurchasedProduct)

    @Query("SELECT * FROM table_purchased_product")
    fun getAll(): Flow<List<DbPurchasedProduct>>

    @Update
    suspend fun updatePurchasedProduct(dbPurchasedProduct: DbPurchasedProduct)

    @Delete
    suspend fun deletePurchasedProduct(dbPurchasedProduct: DbPurchasedProduct)

    @Delete
    suspend fun deleteAll(dbPurchasedProducts: List<DbPurchasedProduct>)
}