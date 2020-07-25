package com.juawapps.whatstowatch.util

import androidx.lifecycle.Observer
import com.juawapps.whatstowatch.common.ui.Event
import com.juawapps.whatstowatch.common.ui.ViewEffect
import com.juawapps.whatstowatch.common.ui.ViewState
import com.juawapps.whatstowatch.common.ui.ViewStateStore
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class ViewModelTestRule<ViewStateType : ViewState, ViewEffectType : ViewEffect> : TestRule {

    private lateinit var viewStateObserver: Observer<ViewStateType>
    private lateinit var viewEffectObserver: Observer<Event<ViewEffectType>>

    override fun apply(base: Statement, description: Description?) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            viewStateObserver = mockk(relaxed = true)
            viewEffectObserver = mockk(relaxed = true)

            base.evaluate()

            confirmVerified(viewStateObserver, viewEffectObserver)
        }
    }

    fun observe(viewModel: ViewStateStore<ViewStateType, ViewEffectType>) {
        viewModel.viewState.observeForever(viewStateObserver)
        viewModel.viewEffect.observeForever(viewEffectObserver)
    }

    fun hadEffect(effect: ViewEffectType) {
        verifySequence {
            viewEffectObserver.onChanged(effect.asEvent())
        }
    }

    fun assertViewStateSequence(vararg viewStates: ViewStateType) {
        verifySequence {
            viewStates.forEach { viewStateObserver.onChanged(it) }
        }
    }
}
