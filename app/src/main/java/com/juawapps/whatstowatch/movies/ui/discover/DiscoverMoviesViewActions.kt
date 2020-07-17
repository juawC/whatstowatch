package com.juawapps.whatstowatch.movies.ui.discover

import com.juawapps.whatstowatch.common.ui.LceViewActions

interface DiscoverMoviesViewActions : LceViewActions {
    fun tapOnMovie(movieId: Long)
}