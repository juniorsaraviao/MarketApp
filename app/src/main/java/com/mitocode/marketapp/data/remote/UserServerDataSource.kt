package com.mitocode.marketapp.data.remote

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.mitocode.marketapp.data.Api
import com.mitocode.marketapp.data.LoginRequest
import com.mitocode.marketapp.domain.User
import javax.inject.Inject

class UserServerDataSource @Inject
constructor(private val remoteService: Api.ApiInterface) : UserRemoteDataSource {
    override suspend fun auth(email: String, password: String, firebaseToken: String): Either<Error, User> {
        try {
            remoteService.auth(LoginRequest(email, password)).right()
        }catch (ex: Exception){
            ex.toString().left()
        }
    }
}