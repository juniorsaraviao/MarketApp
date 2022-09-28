package com.mitocode.marketapp.data.remote

import com.mitocode.marketapp.data.Api
import com.mitocode.marketapp.data.LoginRequest

class UserServerDataSource : UserRemoteDataSource {
    override suspend fun auth(email: String, password: String, firebaseToken: String) {
        Api.build().auth(LoginRequest(email, password))
    }
}