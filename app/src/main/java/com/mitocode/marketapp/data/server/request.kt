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

data class OrderRequest(
    @SerializedName("direccionEnvio")
    val direction: DirectionRequest,
    @SerializedName("metodoPago")
    val paymentMethod: PaymentMethodRequest,
    @SerializedName("fechaHora")
    val dateTime: String,
    @SerializedName("productos")
    val products: List<ProductRequest>,
    val total: Double
)

data class DirectionRequest (
    @SerializedName("tipo")
    val type: Int,
    @SerializedName("direccion")
    val direction: String,
    @SerializedName("referencia")
    val reference: String,
    @SerializedName("distrito")
    val district: String
)

data class PaymentMethodRequest (
    @SerializedName("tipo")
    val type: Int,
    @SerializedName("monto")
    val amount: Double
)

data class ProductRequest (
    @SerializedName("categoriaId")
    val categoryId: String,
    @SerializedName("productoId")
    val productId: String,
    @SerializedName("cantidad")
    val amount: Int
)