package com.mitocode.marketapp.data.remote

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.mitocode.marketapp.data.Api
import com.mitocode.marketapp.Error
import com.mitocode.marketapp.data.LoginRequest
import com.mitocode.marketapp.data.RegisterAccountRequest
import com.mitocode.marketapp.data.UserRemote
import com.mitocode.marketapp.domain.User
import com.mitocode.marketapp.tryCall
import javax.inject.Inject

class UserServerDataSource @Inject
constructor(private val remoteService: Api.ApiInterface) : UserRemoteDataSource {
    override suspend fun auth(email: String, password: String, firebaseToken: String): Either<Error, User> = tryCall {
        val response = remoteService.auth(LoginRequest(email, password))
        response?.let {
            it.data!!.toDomainModel()
        }!!
    }

    override suspend fun createAccount(request: RegisterAccountRequest): Either<Error, User> = tryCall {
        remoteService.registerAccount(request).data!!.toDomainModel()
    }

    private fun UserRemote.toDomainModel(): User = User(uuid, names, surnames, email, phone, gender, numberDocument, type)
}