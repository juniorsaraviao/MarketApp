package com.mitocode.marketapp.data.datasource

import com.mitocode.marketapp.data.local.DbCategory
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.domain.Category
import kotlinx.coroutines.flow.Flow

interface CategoryLocalDataSource {

    val categories: Flow<List<Category>>

    suspend fun isEmpty(): Boolean

    suspend fun count(): Int

    suspend fun save(dbCategories: List<DbCategory>): Error?
}