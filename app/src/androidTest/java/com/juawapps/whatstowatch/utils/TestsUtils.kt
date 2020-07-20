package com.juawapps.whatstowatch.utils

import android.app.Application
import android.util.Log
import androidx.test.core.app.ApplicationProvider

fun getJsonFromAssets(path: String): String? {
    val context = ApplicationProvider.getApplicationContext<Application>()
    Log.d("JsonFromAssets", "Reading path: $path")
    return context.assets.open(path).bufferedReader().use { it.readText() }
}
