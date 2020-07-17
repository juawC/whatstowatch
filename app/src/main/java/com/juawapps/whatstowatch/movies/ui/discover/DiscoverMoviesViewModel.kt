package com.juawapps.whatstowatch.movies.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.data.collectResult
import com.juawapps.whatstowatch.common.ui.DefaultViewStateStore
import com.juawapps.whatstowatch.common.ui.ViewStateStore
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem
import com.juawapps.whatstowatch.movies.domain.usecase.DiscoverMoviesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias DiscoverMoviesStateStore = DefaultViewStateStore<DiscoverMoviesViewState, DiscoverMoviesViewEffect>

class DiscoverMoviesViewModel @Inject constructor(
    private val discoverMoviesUseCase: DiscoverMoviesUseCase,
    private val viewStateStore: DiscoverMoviesStateStore = DefaultViewStateStore(
        DiscoverMoviesViewState()
    )
) : ViewModel(),
    ViewStateStore<DiscoverMoviesViewState, DiscoverMoviesViewEffect> by viewStateStore,
    DiscoverMoviesViewActions {

    init {
        fetchMovies()
    }

    override fun tapOnMovie(movieId: Long) {
        viewStateStore.sendNavigateToDetailEvent(movieId)
    }

    override fun refresh() {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            viewStateStore.displayLoading()

            discoverMoviesUseCase.invoke().collectResult(
                ifFailure = { viewStateStore.displayError(it) },
                ifSuccess = { viewStateStore.displayMovies(it) }
            )
        }
    }

    private fun DiscoverMoviesStateStore.displayError(error: Throwable) {
        updateState {
            copy(
                isLoading = false,
                errorMessage = R.string.error_message
            )
        }
    }

    private fun DiscoverMoviesStateStore.displayMovies(
        list: List<MovieListItem>
    ) {
        updateState {
            copy(
                isLoading = false,
                isRefreshing = false,
                movies = list.map { it.mapToUi() }
            )
        }
    }

    private fun DiscoverMoviesStateStore.displayLoading() {
        updateState {
            copy(
                isLoading = movies.isEmpty(),
                isRefreshing = movies.isNotEmpty()
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