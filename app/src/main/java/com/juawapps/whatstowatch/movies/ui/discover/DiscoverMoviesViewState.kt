package com.juawapps.whatstowatch.movies.ui.discover

import androidx.annotation.StringRes
import com.juawapps.whatstowatch.common.ui.LceViewState
import javax.inject.Inject

data class DiscoverMoviesViewState (
    override val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val movies: List<MovieListUiItem> = emptyList(),
    @StringRes override val errorMessage: Int? = null
) : LceViewState {

    @Inject
    constructor() : this(movies = emptyList())

    val isEmptyView: Boolean
        get() = movies.isEmpty() && !isLoading
}