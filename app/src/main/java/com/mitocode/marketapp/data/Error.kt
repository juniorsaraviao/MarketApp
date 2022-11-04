package com.mitocode.marketapp.data

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import retrofit2.HttpException
import java.io.IOException

sealed class Error {
    data class Server(val code: Int): Error()
    object Connectivity: Error()
    data class Unknown(val message: String): Error()
}

fun Throwable.toError(): Error = when(this){
    is IOException -> Error.Connectivity
    is HttpException -> Error.Server(code())
    else -> Error.Unknown(message ?: "")
}

suspend fun <T> tryCall(action: suspend () -> T): Either<Error, T> = try {
    action().right()
}catch (ex: Exception){
    ex.toError().left()
}

suspend fun <T> tryCallNoReturnData(action: suspend () -> T): Error? = try {
    action()
    null
}catch (ex: Exception){
    ex.toError()
}