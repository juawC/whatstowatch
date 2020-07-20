/*
 * Copyright 2018 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.juawapps.whatstowatch.common.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    fun <R> map(transformation: (T) -> R): Result<R> {
        return when (this) {
            is Success -> Success(transformation(data))
            is Error -> Error(exception)
        }
    }

    fun <R> fold(ifFailure: (Exception) -> R, ifSuccess: (T) -> R): R {
        return when (this) {
            is Success -> ifSuccess(data)
            is Error -> ifFailure(exception)
        }
    }

    suspend fun <R> suspendFold(
        ifFailure: suspend (Exception) -> R,
        ifSuccess: suspend (T) -> R
    ): R {
        return when (this) {
            is Success -> ifSuccess(data)
            is Error -> ifFailure(exception)
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}
