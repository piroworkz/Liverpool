package com.davidluna.liverpool.domain.entities

import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class AppError() : Throwable() {
    class NetworkError : AppError()
    class ServerError : AppError()
    class UnknownError : AppError()
}


fun Throwable.toAppError(): AppError {
    return when (this) {
        is UnknownHostException -> AppError.NetworkError()
        is SocketTimeoutException -> AppError.ServerError()
        else -> AppError.UnknownError()
    }
}