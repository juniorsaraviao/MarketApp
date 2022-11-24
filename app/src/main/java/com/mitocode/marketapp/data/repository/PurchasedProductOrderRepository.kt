package com.mitocode.marketapp.data.repository

import arrow.core.Either
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.datasource.PurchasedProductRemoteDataSource
import com.mitocode.marketapp.data.server.OrderRequest
import com.mitocode.marketapp.data.server.WrappedResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PurchasedProductOrderRepository
@Inject constructor(private val purchasedProductRemoteDataSource: PurchasedProductRemoteDataSource){

    suspend fun savePurchasedProductOrder(request: OrderRequest): Flow<Either<Error, WrappedResponse<String>>> {
        return flow {
            emit(purchasedProductRemoteDataSource.saveProductProductOrder(request))
        }
    }
}