package com.mitocode.marketapp.data.repository

import arrow.core.Either
import com.mitocode.marketapp.domain.Product
import kotlinx.coroutines.flow.Flow
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.datasource.ProductRemoteDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productRemoteDataSource: ProductRemoteDataSource) {

    suspend fun getProduct(categoryId: String): Flow<Either<Error, List<Product>>> {
        return flow {
            emit(productRemoteDataSource.getProduct(categoryId))
        }
    }
}