package com.juawapps.whatstowatch.movies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.ui.DefaultViewStateStore
import com.juawapps.whatstowatch.common.ui.LceViewActions
import com.juawapps.whatstowatch.common.ui.ViewStateStore
import com.juawapps.whatstowatch.di.ViewModelFactoryCreator
import com.juawapps.whatstowatch.movies.domain.model.MovieDetails
import com.juawapps.whatstowatch.movies.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias MoviesDetailStateStore = DefaultViewStateStore<MoviesDetailsViewState, MovieDetailsViewEffect>

class MovieDetailsViewModel(
    private val movieId: Long,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val viewStateStore: MoviesDetailStateStore = DefaultViewStateStore(
        MoviesDetailsViewState()
    )
) : ViewModel(),
    ViewStateStore<MoviesDetailsViewState, MovieDetailsViewEffect> by viewStateStore,
    LceViewActions {

    init {
        loadDetails()
    }

    override fun refresh() {
        loadDetails()
    }

    private fun loadDetails() {
        viewModelScope.launch {
            viewStateStore.displayLoading()
            getMovieDetailsUseCase(movieId).fold(
                ifFailure = { viewStateStore.displayError(it) },
                ifSuccess = { viewStateStore.displayMovies(it) }
            )
        }
    }

    private fun MoviesDetailStateStore.displayError(error: Throwable) {
        val shouldErrorBeFullScreen = currentState.movie == MovieDetailsUiItem.EMPTY

        updateState {
            copy(
                isLoading = false,
                isRefreshing = false,
                errorMessage = if (shouldErrorBeFullScreen) R.string.error_message else null
            )
        }

        if (!shouldErrorBeFullScreen) sendEffect(MovieDetailsViewEffect.ShowErrorMessage)
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