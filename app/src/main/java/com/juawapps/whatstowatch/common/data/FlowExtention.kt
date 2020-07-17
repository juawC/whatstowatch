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