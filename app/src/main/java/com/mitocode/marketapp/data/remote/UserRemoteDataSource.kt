package com.mitocode.marketapp.data.remote

import arrow.core.Either
import com.mitocode.marketapp.domain.User

interface UserRemoteDataSource {

    suspend fun auth(email: String, password: String, firebaseToken: String): Either<Error, User>

}