package com.juawapps.whatstowatch.movies.ui.detail

import com.juawapps.whatstowatch.common.ui.ViewEffect

sealed class MovieDetailsViewEffect : ViewEffect {
    object ShowErrorMessage : MovieDetailsViewEffect()
}