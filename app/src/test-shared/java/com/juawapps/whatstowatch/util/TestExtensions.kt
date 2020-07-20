package com.juawapps.whatstowatch.util

import com.juawapps.whatstowatch.common.data.Result
import com.juawapps.whatstowatch.common.ui.Event
import com.juawapps.whatstowatch.movies.data.model.ListResponseWrapper
import okhttp3.internal.http.RealResponseBody
import okio.Buffer
import retrofit2.Response

fun <T> T.asSuccess(): Result<T> {
    return Result.Success(this)
}

fun <T, E : Exception> E.asError(): Result<T> {
    return Result.Error(this)
}

fun <T> T.asEvent(): Event<T> {
    return Event(this)
}

fun <T> T.asList(): List<T> {
    return listOf(this)
}

fun <T> Int.asResponseError(
    body: okhttp3.ResponseBody = RealResponseBody(null, 0, Buffer())
): Response<T> = Response.error(this, body)

fun <T> T.asResponseSuccess(): Response<T> = Response.success(this)

fun <T> List<T>.wrapWithListResponse(): ListResponseWrapper<T> = ListResponseWrapper(
    items = this,
    page = 0,
    totalResults = 0,
    totalPages = 0
)