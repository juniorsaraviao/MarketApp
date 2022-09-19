package com.mitocode.marketapp.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST

object Api {

    // Endpoint = UrlBase + Method
    // UrlBase = https://marketapp2021.herokuapp.com/
    // Endpoint = api/usuarios/login

    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl("https://marketapp2021.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())

    interface ApiInterface {
        @POST("/api/usuarios/login")
        suspend fun auth(@Body request: LoginRequest) : Response<WrappedResponse<UserRemote>>
    }

    fun build(): ApiInterface{
        return builder.build().create(ApiInterface::class.java)
    }
}