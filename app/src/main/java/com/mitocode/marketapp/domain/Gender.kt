package com.mitocode.marketapp.domain

data class Gender(
    val gender: String,
    val description: String
){
    override fun toString(): String {
        return description
    }
}