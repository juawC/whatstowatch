package com.juawapps.whatstowatch.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ViewModelFactoryCreator {

    fun <VM : ViewModel> create(viewModelCreator: () -> VM): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return viewModelCreator() as T
            }
        }
    }
}
