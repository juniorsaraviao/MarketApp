package com.mitocode.marketapp.data

data class LoginRequest (
    val email: String,
    val password: String,
    val firebaseToken: String=""
)