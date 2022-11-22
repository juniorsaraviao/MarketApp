package com.mitocode.marketapp.data.datasource

import android.content.SharedPreferences
import arrow.core.Either
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.server.OrderRequest
import com.mitocode.marketapp.data.server.RemoteService
import com.mitocode.marketapp.data.server.WrappedResponse
import com.mitocode.marketapp.data.tryCall
import com.mitocode.marketapp.util.Constants
import javax.inject.Inject

class PurchasedProductServerDataSource @Inject constructor(
    private val remoteService: RemoteService,
    private val sharedPreferences: SharedPreferences
): PurchasedProductRemoteDataSource {

    override suspend fun saveProductProductOrder(orderRequest: OrderRequest): Either<Error, WrappedResponse<String>> = tryCall {
        val token = sharedPreferences.getString(Constants.TOKEN, "") ?: ""
        remoteService.savePurchasedProductsOrder(token, orderRequest)
    }

}