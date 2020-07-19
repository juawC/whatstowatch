package com.juawapps.whatstowatch.common.data

sealed class Resource<out T> {

    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading<out T>(val data: T?) : Resource<T>()
    data class Error<out T>(val exception: Exception, val data: T?) : Resource<T>()

    fun <R> map(transformation: (T) -> R): Resource<R> {
        return when (this) {
            is Success -> Success(transformation(data))
            is Loading -> Loading(data?.run(transformation))
            is Error -> Error(exception, data?.run(transformation))
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Loading<*> -> "Loading[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}