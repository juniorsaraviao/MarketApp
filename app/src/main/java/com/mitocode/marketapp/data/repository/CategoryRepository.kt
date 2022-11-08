package com.mitocode.marketapp.data.repository

import arrow.core.Either
import com.mitocode.marketapp.data.datasource.CategoryRemoteDataSource
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.datasource.CategoryLocalDataSource
import com.mitocode.marketapp.data.local.DbCategory
import com.mitocode.marketapp.data.server.RegisterCategoryRequest
import com.mitocode.marketapp.data.server.WrappedResponse
import com.mitocode.marketapp.data.tryCallNoReturnData
import com.mitocode.marketapp.domain.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepository
@Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val categoryLocalDataSource: CategoryLocalDataSource)
{
    val categories = categoryLocalDataSource.categories

    suspend fun requestCategories(): Error? = tryCallNoReturnData {
        /*if (categoryLocalDataSource.isEmpty()){
            val categories = categoryRemoteDataSource.getCategories()
            categories.fold(
                {
                    null
                },
                { categoriesDomain ->
                    categoryLocalDataSource.save(categoriesDomain.toLocalModel())
                }
            )
        }*/

        val categories = categoryRemoteDataSource.getCategories()
        categories.fold(
            {
                null
            },
            { categoriesDomain ->

                val countRemote = categoriesDomain.size
                val countLocal = categoryLocalDataSource.count()

                if (countRemote > countLocal){
                    categoryLocalDataSource.save(categoriesDomain.toLocalModel())
                }
            }
        )
    }

    suspend fun saveCategory(request: RegisterCategoryRequest): Flow<Either<Error, WrappedResponse<Nothing>>>{
        return flow {
            emit(categoryRemoteDataSource.saveCategory(request))
        }
    }

    /* suspend fun getCategories(): Flow<Either<Error, List<Category>>> {
        return flow {
            emit(categoryRemoteDataSource.getCategories())
        }

    } */

    private fun List<Category>.toLocalModel(): List<DbCategory> = map { it.toLocalModel() }
    private fun Category.toLocalModel(): DbCategory = DbCategory(uuid, name, cover)
}