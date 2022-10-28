package com.mitocode.marketapp.data.server

import retrofit2.http.*

interface RemoteService {
    @POST("/api/usuarios/login")
    suspend fun auth(@Body request: LoginRequest): WrappedResponse<UserRemote>

    @GET("api/usuarios/obtener-generos")
    suspend fun getGenders(): WrappedListResponse<GenderRemote>

    @POST("/api/usuarios/crear-cuenta")
    suspend fun registerAccount(@Body request: RegisterAccountRequest): WrappedResponse<UserRemote>

    @GET("/api/categorias")
    suspend fun getCategories(@Header("Authorization") token: String): WrappedListResponse<CategoryRemote>

    @GET("/api/categorias/{categoriaId}/productos")
    suspend fun getProducts(
        @Header("Authorization") token: String,
        @Path("categoriaId") categoriaId: String
    ): WrappedListResponse<ProductRemote>

    //? QueryParams api/categories?categoriaId=22&name=Hola - Example
    @GET("api/desembarque")
    suspend fun getData(
        @Query("idUser") idUser: Int,
        @Query("fechaInicio") fechaInicio: String,
        @Query("fechaFin") fechaFin: String
    )
}