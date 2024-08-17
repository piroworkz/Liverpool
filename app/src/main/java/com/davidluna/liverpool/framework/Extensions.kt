package com.davidluna.liverpool.framework

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.davidluna.liverpool.domain.entities.AppError
import com.davidluna.liverpool.domain.entities.toAppError

suspend fun <T> tryCatch(block: suspend () -> T): Either<AppError, T> =
    try {
        block().right()
    } catch (e: Throwable) {
        e.printStackTrace()
        e.toAppError().left()
    }