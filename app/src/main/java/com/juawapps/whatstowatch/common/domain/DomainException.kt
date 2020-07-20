package com.juawapps.whatstowatch.common.domain

sealed class DomainException : Exception() {
    data class HttpError(val code: Int, val errorMessage: String) : DomainException()
    data class NetworkError(val throwable: Throwable) : DomainException()
}