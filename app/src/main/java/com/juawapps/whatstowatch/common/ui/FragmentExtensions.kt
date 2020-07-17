package com.juawapps.whatstowatch.common.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
    factory: ViewModelProvider.Factory
): VM {
    return ViewModelProvider(this, factory).get(VM::class.java)
}