package com.juawapps.whatstowatch.movies.ui.detail

import androidx.annotation.StringRes
import com.juawapps.whatstowatch.common.ui.LceViewState
import javax.inject.Inject

data class MoviesDetailsViewState (
    override val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val movie: MovieDetailsUiItem = MovieDetailsUiItem.EMPTY,
    @StringRes override val errorMessage: Int? = null
) : LceViewState {

    @Inject
    constructor() : this(isLoading = false)
}