package com.mitocode.marketapp.data.datasource

import arrow.core.Either
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.domain.Category

interface CategoryRemoteDataSource {

    suspend fun getCategories(): Either<Error, List<Category>>
}