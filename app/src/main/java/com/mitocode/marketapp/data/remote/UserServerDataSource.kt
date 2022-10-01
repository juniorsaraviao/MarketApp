package com.mitocode.marketapp.data.remote

import com.mitocode.marketapp.data.Api
import com.mitocode.marketapp.data.LoginRequest
import javax.inject.Inject

class UserServerDataSource @Inject
constructor(private val remoteService: Api.ApiInterface) : UserRemoteDataSource {
    override suspend fun auth(email: String, password: String, firebaseToken: String) {
        remoteService.auth(LoginRequest(email, password))
    }
}