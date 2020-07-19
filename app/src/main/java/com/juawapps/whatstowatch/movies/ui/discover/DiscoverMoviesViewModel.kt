package com.juawapps.whatstowatch.movies.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.data.collectResult
import com.juawapps.whatstowatch.common.ui.DefaultViewStateStore
import com.juawapps.whatstowatch.common.ui.ViewStateStore
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem
import com.juawapps.whatstowatch.movies.domain.usecase.DiscoverMoviesUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias DiscoverMoviesStateStore = DefaultViewStateStore<DiscoverMoviesViewState, DiscoverMoviesViewEffect>

// open is needed here in order to mock the ViewModel
// TODO: Investigate if it is possible to configure mockk so we don't need to use the open keyword
open class DiscoverMoviesViewModel @Inject constructor(
    private val discoverMoviesUseCase: DiscoverMoviesUseCase,
    private val viewStateStore: DiscoverMoviesStateStore = DefaultViewStateStore(
        DiscoverMoviesViewState()
    )
) : ViewModel(),
    ViewStateStore<DiscoverMoviesViewState, DiscoverMoviesViewEffect> by viewStateStore,
    DiscoverMoviesViewActions {

    private val fetchMoviesListChannel = Channel<Unit>(Channel.CONFLATED)

    init {
        viewModelScope.launch {
            fetchMoviesListChannel.consumeAsFlow().flatMapLatest {
                viewStateStore.displayLoading()
                discoverMoviesUseCase.invoke()
            }.collectResult(
                ifFailure = { viewStateStore.displayError(it) },
                ifSuccess = { viewStateStore.displayMovies(it) }
            )
        }

        viewModelScope.launch { fetchMoviesListChannel.send(Unit) }
    }

    override fun tapOnMovie(movieId: Long) {
        viewStateStore.sendNavigateToDetailEvent(movieId)
    }

    override fun refresh() {
        viewModelScope.launch { fetchMoviesListChannel.send(Unit) }
    }

    private fun DiscoverMoviesStateStore.displayError(error: Throwable) {
        val shouldErrorSateBePermanent = currentState.movies.isEmpty()

        updateState {
            copy(
                isLoading = false,
                isRefreshing = false,
                errorMessage = if (shouldErrorSateBePermanent) R.string.error_message else null
            )
        }

        if (!shouldErrorSateBePermanent) sendEffect(DiscoverMoviesViewEffect.ShowErrorMessage)
    }

    private fun DiscoverMoviesStateStore.displayMovies(
        list: List<MovieListItem>
    ) {
        updateState {
            copy(
                isLoading = false,
                isRefreshing = false,
                movies = list.map(MovieListUiItem.Factory::create),
                errorMessage = null
            )
        }
    }

    private fun DiscoverMoviesStateStore.displayLoading() {
        updateState {
            val isRefresh = movies.isNotEmpty()

            copy(
                isLoading = !isRefresh,
                isRefreshing = isRefresh
            )
        }
    }

    private fun DiscoverMoviesStateStore.sendNavigateToDetailEvent(
        movieId: Long
    ) {
        sendEffect(
            DiscoverMoviesViewEffect.NavigateToDetail(movieId)
        )
    }
}