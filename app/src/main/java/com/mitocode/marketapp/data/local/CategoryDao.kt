package com.mitocode.marketapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(dbCategories: List<DbCategory>)

    // not need to use suspend because flow works on its own thread
    @Query("SELECT * FROM table_category")
    fun getAll(): Flow<List<DbCategory>>

    @Query("SELECT COUNT(uuid) FROM table_category")
    fun categoriesCount(): Int

    @Query("SELECT COUNT(uuid) FROM table_category WHERE uuid = :id")
    fun findByUuid(id: String)

    @Update
    suspend fun updateCategory(dbCategory: DbCategory)

    @Delete
    suspend fun deleteCategory(dbCategory: DbCategory)
}