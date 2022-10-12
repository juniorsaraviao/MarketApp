package com.mitocode.marketapp.data.datasource

import arrow.core.Either
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.server.RegisterAccountRequest
import com.mitocode.marketapp.domain.User

interface UserRemoteDataSource {

    suspend fun auth(email: String, password: String, firebaseToken: String): Either<com.mitocode.marketapp.data.Error, User>

    suspend fun createAccount(request: RegisterAccountRequest): Either<Error, User>

}