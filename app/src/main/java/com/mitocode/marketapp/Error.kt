package com.mitocode.marketapp

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.mitocode.marketapp.domain.User
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