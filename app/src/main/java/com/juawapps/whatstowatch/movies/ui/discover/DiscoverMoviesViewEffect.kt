package com.juawapps.whatstowatch.movies.ui.discover

import com.juawapps.whatstowatch.common.ui.ViewEffect

sealed class DiscoverMoviesViewEffect : ViewEffect {
    data class NavigateToDetail(val movieId: Long) : DiscoverMoviesViewEffect()
}