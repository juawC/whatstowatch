package com.juawapps.whatstowatch.common.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
    factory: ViewModelProvider.Factory
): VM {
    return ViewModelProvider(this, factory).get(VM::class.java)
}

fun <VS: ViewState, VF: ViewEffect> Fragment.observeEffects(
    viewStateStore: ViewStateStore<VS, VF>,
    body: VF.() -> Unit
) {
    viewLifecycleOwner.observeEvent(viewStateStore.viewEffect) { effect ->
        effect.apply(body)
    }
}