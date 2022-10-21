package com.mitocode.marketapp.data.server

import com.google.gson.annotations.SerializedName

data class UserRemote (
    @SerializedName("uuid")
    val uuid:String,
    @SerializedName("nombres")
    val names: String,
    @SerializedName("apellidos")
    val surnames: String,
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

data class GenderRemote(
    @SerializedName("genero")
    val gender: String,
    @SerializedName("descripcion")
    val description: String
){
    override fun toString(): String {
        return description
    }
}

data class CategoryRemote(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("nombre")
    val name: String,
    @SerializedName("cover")
    val cover: String
)