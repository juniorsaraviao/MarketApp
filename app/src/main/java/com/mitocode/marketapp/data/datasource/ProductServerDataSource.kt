package com.mitocode.marketapp.data.datasource

import android.content.SharedPreferences
import arrow.core.Either
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.server.ProductRemote
import com.mitocode.marketapp.data.server.RemoteService
import com.mitocode.marketapp.data.tryCall
import com.mitocode.marketapp.domain.Product
import com.mitocode.marketapp.util.Constants
import javax.inject.Inject

class ProductServerDataSource @Inject constructor(
    private val remoteService: RemoteService,
    private val sharedPreferences: SharedPreferences
) : ProductRemoteDataSource {

    private lateinit var categoryId: String

    override suspend fun getProduct(categoryId: String): Either<Error, List<Product>> = tryCall {
        this.categoryId = categoryId
        val token = sharedPreferences.getString(Constants.TOKEN, "") ?: ""
        val response = remoteService.getProducts("Bearer $token", categoryId)
        response?.let {
            it.data!!.toDomainModel()
        }!!
    }

    private fun List<ProductRemote>.toDomainModel(): List<Product> = map { it.toDomainModel() }
    private fun ProductRemote.toDomainModel(): Product = Product(categoryId, uuid, description, code, features, price, stock, images, amount)
}