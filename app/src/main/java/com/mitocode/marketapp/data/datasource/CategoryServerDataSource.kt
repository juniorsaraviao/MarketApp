package com.mitocode.marketapp.data.datasource

import android.content.SharedPreferences
import arrow.core.Either
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.server.CategoryRemote
import com.mitocode.marketapp.data.server.RegisterCategoryRequest
import com.mitocode.marketapp.data.server.RemoteService
import com.mitocode.marketapp.data.server.WrappedResponse
import com.mitocode.marketapp.data.tryCall
import com.mitocode.marketapp.domain.Category
import com.mitocode.marketapp.util.Constants
import javax.inject.Inject

class CategoryServerDataSource @Inject constructor(
    private val remoteService: RemoteService,
    private val sharedPreferences: SharedPreferences
) : CategoryRemoteDataSource {

    override suspend fun getCategories(): Either<Error, List<Category>> = tryCall {
        val token = sharedPreferences.getString(Constants.TOKEN, "") ?: ""
        val response = remoteService.getCategories("Bearer $token")
        response?.let {
            it.data!!.toDomainModel()
        }!!
    }

    override suspend fun saveCategory(request: RegisterCategoryRequest): Either<Error, WrappedResponse<Nothing>> = tryCall{
        val token = sharedPreferences.getString(Constants.TOKEN, "") ?: ""
        remoteService.createCategory("Bearer $token", request)
    }

    private fun List<CategoryRemote>.toDomainModel(): List<Category> = map { it.toDomainModel() }

    private fun CategoryRemote.toDomainModel(): Category = Category(uuid, name, cover)
}