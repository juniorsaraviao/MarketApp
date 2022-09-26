package com.mitocode.marketapp.data

import com.google.gson.annotations.SerializedName

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