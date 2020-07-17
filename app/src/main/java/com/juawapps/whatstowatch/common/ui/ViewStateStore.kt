package com.juawapps.whatstowatch.common.ui

import androidx.lifecycle.LiveData

interface ViewStateStore<VS : ViewState, VE : ViewEffect> {
    val viewState: LiveData<VS>
    val viewEffect: LiveData<Event<VE>>
}
