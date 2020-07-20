package com.juawapps.whatstowatch.common.data

import retrofit2.Response
import java.io.IOException

fun <T> Response<T>.toResult(): Result<T> {

    if (isSuccessful) {
        val body = body()
        if (body != null) {
            return Result.Success(body)
        }
    }

    return Result.Error(
        IOException("HTTP ERROR: ${code()} ${message()}")
    )
}

fun <T, R> Response<T>.toResult(mapper: (T) -> R): Result<R> {
    return toResult().map(mapper)
}

fun <T, R> (suspend () -> Response<T>).wrapWithResult(
    mapper: (T) -> R
): suspend () -> Result<R>  = {
    try {
        this().toResult().map(mapper)
    } catch (exception: Exception) {
        Result.Error(exception)
    }
}

