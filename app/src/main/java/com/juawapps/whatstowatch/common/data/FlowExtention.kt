package com.juawapps.whatstowatch.common.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import java.lang.Exception

suspend fun <T> Flow<Result<T>>.collectResult(
    ifFailure: (Exception) -> Unit,
    ifSuccess: (T) -> Unit
) {
    collect { result ->
        result.fold(ifFailure, ifSuccess)
    }
}

suspend fun <T> Flow<Resource<List<T>>>.collectResource(
    ifFailure: (Exception, List<T>) -> Unit,
    ifSuccess: (List<T>) -> Unit,
    ifLoading: (List<T>) -> Unit
) {
    collect { resource ->
        when (resource) {
            is Resource.Success -> ifSuccess(resource.data)
            is Resource.Loading -> ifLoading(resource.data ?: emptyList())
            is Resource.Error -> ifFailure(resource.exception, resource.data ?: emptyList())
        }
    }
}