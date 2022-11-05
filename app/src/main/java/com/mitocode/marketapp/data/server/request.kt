package com.mitocode.marketapp.data.server

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val password: String,
    val firebaseToken: String = ""
)

data class RegisterAccountRequest(
    @SerializedName("nombres")
    val names: String,
    @SerializedName("apellidos")
    val surnames: String,
    val email: String,
    val password: String,
    @SerializedName("celular")
    val phone: String,
    @SerializedName("genero")
    val gender: String,
    @SerializedName("nroDoc")
    val numberDocument: String,
    val firebaseToken: String = ""
)

data class RegisterCategoryRequest(
    @SerializedName("nombre")
    val name: String,
    val cover: String
)