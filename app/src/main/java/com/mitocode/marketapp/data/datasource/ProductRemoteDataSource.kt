package com.mitocode.marketapp.data.datasource

import arrow.core.Either
import com.mitocode.marketapp.domain.Product
import com.mitocode.marketapp.data.Error

interface ProductRemoteDataSource {

    suspend fun getProduct(categoryId: String): Either<Error, List<Product>>
}