package com.mitocode.marketapp.data.remote

import arrow.core.Either
import com.mitocode.marketapp.Error
import com.mitocode.marketapp.data.RegisterAccountRequest
import com.mitocode.marketapp.domain.User

interface UserRemoteDataSource {

    suspend fun auth(email: String, password: String, firebaseToken: String): Either<Error, User>

    suspend fun createAccount(request: RegisterAccountRequest): Either<Error, User>

}