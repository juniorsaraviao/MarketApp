package com.mitocode.marketapp.data.remote

interface UserRemoteDataSource {

    suspend fun auth(email: String, password: String, firebaseToken: String)

}