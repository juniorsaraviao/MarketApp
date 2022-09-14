package com.mitocode.marketapp.data

import com.google.gson.annotations.SerializedName

data class UserRemote (
    @SerializedName("uuid")
    val uuid:String,
    @SerializedName("nombres")
    val names: String,
    @SerializedName("apellidos")
    val surname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("celular")
    val phone: String,
    @SerializedName("genero")
    val gender: String,
    @SerializedName("nroDoc")
    val numberDocument: String,
    @SerializedName("tipo")
    val type: String
)