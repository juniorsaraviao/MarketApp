package com.mitocode.marketapp.data.repository

import com.mitocode.marketapp.data.server.RegisterAccountRequest
import com.mitocode.marketapp.data.datasource.UserRemoteDataSource
import javax.inject.Inject

class UserRepository @Inject
constructor(private val userRemoteDataSource: UserRemoteDataSource) {

    suspend fun requestAuth(email: String, password: String, firebaseToken: String)= userRemoteDataSource.auth(email, password, firebaseToken)

    suspend fun createAccount(request: RegisterAccountRequest) = userRemoteDataSource.createAccount(request)
}