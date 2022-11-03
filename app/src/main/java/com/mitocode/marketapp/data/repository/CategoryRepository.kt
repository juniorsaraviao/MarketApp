package com.mitocode.marketapp.data.repository

import arrow.core.Either
import com.mitocode.marketapp.data.datasource.CategoryRemoteDataSource
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.datasource.CategoryLocalDataSource
import com.mitocode.marketapp.domain.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepository
@Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val categoryLocalDataSource: CategoryLocalDataSource)
{

    suspend fun getCategories(): Flow<Either<Error, List<Category>>> {
        return flow {
            emit(categoryRemoteDataSource.getCategories())
        }

    }
}