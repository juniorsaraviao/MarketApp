package com.mitocode.marketapp.data.datasource

import arrow.core.Either
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.server.RegisterCategoryRequest
import com.mitocode.marketapp.data.server.WrappedResponse
import com.mitocode.marketapp.domain.Category

interface CategoryRemoteDataSource {

    suspend fun getCategories(): Either<Error, List<Category>>

    suspend fun saveCategory(request: RegisterCategoryRequest): Either<Error, WrappedResponse<Nothing>>
}