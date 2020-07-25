package com.juawapps.whatstowatch.utils

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.juawapps.whatstowatch.common.ui.Event
import com.juawapps.whatstowatch.common.ui.ViewEffect
import com.juawapps.whatstowatch.common.ui.ViewState
import com.juawapps.whatstowatch.util.asEvent
import io.mockk.verifySequence

interface ViewModelTest<
        ViewModelType : ViewModel,
        ViewEffectType : ViewEffect,
        ViewStateType : ViewState
        > {

    var viewStateObserver: Observer<ViewStateType>
    var viewEffectObserver: Observer<Event<ViewEffectType>>

    fun Observer<Event<ViewEffectType>>.hadEffect(effect: ViewEffectType) {
        verifySequence {
            this@hadEffect.onChanged(effect.asEvent())
        }
    }

    fun Observer<ViewStateType>.assertSequence(vararg viewStates: ViewStateType) {
        verifySequence {
            viewStates.forEach { this@assertSequence.onChanged(it) }
        }
    }
}