package com.juawapps.whatstowatch.common.data

import com.juawapps.whatstowatch.common.domain.DomainException
import retrofit2.Response

fun <T> Response<T>.toResult(): Result<T> {

    if (isSuccessful) {
        val body = body()
        if (body != null) {
            return Result.Success(body)
        }
    }

    return Result.Error(
        DomainException.HttpError(code(), message())
    )
}

fun <T, R> Response<T>.toResult(mapper: (T) -> R): Result<R> {
    return toResult().map(mapper)
}

suspend fun <T, R> (suspend () -> Response<T>).wrapWithResult(
    mapper: (T) -> R
): Result<R>  =
    try {
        this().toResult().map(mapper)
    } catch (exception: Exception) {
        Result.Error(DomainException.NetworkError(exception))
    }