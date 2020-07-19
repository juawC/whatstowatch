package com.juawapps.whatstowatch.util

import com.juawapps.whatstowatch.common.data.Resource
import com.juawapps.whatstowatch.common.data.Result
import com.juawapps.whatstowatch.common.ui.Event

fun <T> T.asSuccess(): Result<T> {
    return Result.Success(this)
}

fun <T> T.asResourceSuccess(): Resource<T> {
    return Resource.Success(this)
}

fun <T, E : Exception> E.asResourceError(data: T): Resource<T> {
    return Resource.Error(this, data)
}

fun <T> T.asResourceLoading(): Resource<T> {
    return Resource.Loading(this)
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