package com.mitocode.marketapp.data.server

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RemoteService {
    @POST("/api/usuarios/login")
    suspend fun auth(@Body request: LoginRequest): WrappedResponse<UserRemote>

    @GET("api/usuarios/obtener-generos")
    suspend fun getGenders(): WrappedListResponse<GenderRemote>

    @POST("/api/usuarios/crear-cuenta")
    suspend fun registerAccount(@Body request: RegisterAccountRequest): WrappedResponse<UserRemote>
}