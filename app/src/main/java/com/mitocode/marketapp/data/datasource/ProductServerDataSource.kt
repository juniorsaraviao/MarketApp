package com.mitocode.marketapp.data.datasource

import arrow.core.Either
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.domain.Product

class ProductServerDataSource: ProductRemoteDataSource {
    override suspend fun getProduct(categoryId: String): Either<Error, List<Product>> {
        TODO("Not yet implemented")
    }
}