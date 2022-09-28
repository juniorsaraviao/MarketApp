package com.mitocode.marketapp.data

import com.mitocode.marketapp.data.remote.UserServerDataSource

class UserRepository {

    val userServerDataSource = UserServerDataSource()

    suspend fun requestAuth(email: String, password: String, firebaseToken: String){
        return userServerDataSource.auth(email, password, firebaseToken)
    }
}