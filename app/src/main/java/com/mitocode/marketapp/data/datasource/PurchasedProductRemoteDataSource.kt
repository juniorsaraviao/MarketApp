package com.mitocode.marketapp.data.datasource

import arrow.core.Either
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.server.OrderRequest
import com.mitocode.marketapp.data.server.WrappedResponse

interface PurchasedProductRemoteDataSource {

    suspend fun saveProductProductOrder(orderRequest: OrderRequest): Either<Error, WrappedResponse<String>>
}