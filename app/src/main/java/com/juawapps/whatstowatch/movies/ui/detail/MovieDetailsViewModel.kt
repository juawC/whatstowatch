package com.juawapps.whatstowatch.movies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.data.collectResult
import com.juawapps.whatstowatch.common.ui.DefaultViewStateStore
import com.juawapps.whatstowatch.common.ui.LceViewActions
import com.juawapps.whatstowatch.common.ui.ViewStateStore
import com.juawapps.whatstowatch.di.ViewModelFactoryCreator
import com.juawapps.whatstowatch.movies.domain.model.MovieDetails
import com.juawapps.whatstowatch.movies.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias MoviesDetailStateStore = DefaultViewStateStore<MoviesDetailsViewState, MovieDetailsViewEffect>

// open is needed here in order to mock the ViewModel
// TODO: Investigate if it is possible to configure mockk so we don't need to use the open keyword
open class MovieDetailsViewModel(
    private val movieId: Long,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val viewStateStore: MoviesDetailStateStore = DefaultViewStateStore(
        MoviesDetailsViewState()
    )
) : ViewModel(),
    ViewStateStore<MoviesDetailsViewState, MovieDetailsViewEffect> by viewStateStore,
    LceViewActions {

    private val fetchMovieChannel = Channel<Unit>(Channel.CONFLATED)

    init {
        viewModelScope.launch {
            fetchMovieChannel.consumeAsFlow().flatMapLatest {
                viewStateStore.displayLoading()
                getMovieDetailsUseCase.invoke(movieId)
            }.collectResult(
                ifFailure = { viewStateStore.displayError(it) },
                ifSuccess = { viewStateStore.displayMovies(it) }
            )
        }

        viewModelScope.launch { fetchMovieChannel.send(Unit) }
    }

    override fun refresh() {
        viewModelScope.launch { fetchMovieChannel.send(Unit) }
    }

    private fun MoviesDetailStateStore.displayError(error: Throwable) {
        val shouldErrorSateBePermanent = currentState.movie == MovieDetailsUiItem.EMPTY

        updateState {
            copy(
                isLoading = false,
                isRefreshing = false,
                errorMessage = if (shouldErrorSateBePermanent) R.string.error_message else null
            )
        }

        if (!shouldErrorSateBePermanent) sendEffect(MovieDetailsViewEffect.ShowErrorMessage)
    }

    private fun MoviesDetailStateStore.displayMovies(
         movie: MovieDetails
    ) {
        updateState {
            copy(
                isLoading = false,
                isRefreshing = false,
                movie = MovieDetailsUiItem.create(movie),
                errorMessage = null
            )
        }
    }

    private fun MoviesDetailStateStore.displayLoading() {
        updateState {
            val isRefresh = currentState.movie != MovieDetailsUiItem.EMPTY

            copy(
                isLoading = !isRefresh,
                isRefreshing = isRefresh
            )
        }
    }

    class Factory @Inject constructor(
        private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
        private val viewStateStore: MoviesDetailStateStore
    ) {
        fun create(movieId: Long) =
            ViewModelFactoryCreator.create {
                MovieDetailsViewModel(movieId, getMovieDetailsUseCase, viewStateStore)
            }
    }
}