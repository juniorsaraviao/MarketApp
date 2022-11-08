package com.mitocode.marketapp.data.datasource

import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.local.CategoryDao
import com.mitocode.marketapp.data.local.DbCategory
import com.mitocode.marketapp.data.tryCallNoReturnData
import com.mitocode.marketapp.domain.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryLocalDataSourceImp @Inject constructor(private val categoryDao: CategoryDao): CategoryLocalDataSource {
    override val categories: Flow<List<Category>> = categoryDao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = categoryDao.categoriesCount() == 0

    override suspend fun count(): Int = categoryDao.categoriesCount()

    override suspend fun save(dbCategories: List<DbCategory>): Error? = tryCallNoReturnData {
        categoryDao.insertCategories(dbCategories)
    }

    private fun List<DbCategory>.toDomainModel() : List<Category> = map{ it.toDomainModel() }
    private fun DbCategory.toDomainModel() : Category = Category(uuid, name, cover)
}