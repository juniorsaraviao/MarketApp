package com.mitocode.marketapp.data.datasource

import arrow.core.Either
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.server.LoginRequest
import com.mitocode.marketapp.data.server.RegisterAccountRequest
import com.mitocode.marketapp.data.server.RemoteService
import com.mitocode.marketapp.data.server.UserRemote
import com.mitocode.marketapp.data.tryCall
import com.mitocode.marketapp.domain.User
import javax.inject.Inject

class UserServerDataSource @Inject
constructor(private val remoteService: RemoteService) : UserRemoteDataSource {
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