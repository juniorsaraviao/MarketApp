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
    suspend fun updateCategory(DbPurchasedProduct: DbPurchasedProduct)

    @Delete
    suspend fun deleteCategory(DbPurchasedProduct: DbPurchasedProduct)
}