package com.juawapps.whatstowatch.common.data

fun <T> T.asResourceSuccess(): Resource<T> {
    return Resource.Success(this)
}

fun <T, E : Exception> E.asResourceError(data: T): Resource<T> {
    return Resource.Error(this, data)
}

fun <T> T.asResourceLoading(): Resource<T> {
    return Resource.Loading(this)
}