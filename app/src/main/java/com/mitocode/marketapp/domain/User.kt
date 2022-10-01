package com.mitocode.marketapp.domain

data class User (
    val uuid:String,
    val names: String,
    val surnames: String,
    val email: String,
    val phone: String,
    val gender: String,
    val numberDocument: String,
    val type: String
)