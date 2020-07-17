package com.juawapps.whatstowatch.common.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <R, T> LiveData<T>.map(mapFunction: (T) -> R): LiveData<R> {
    return Transformations.map(this, mapFunction)
}
