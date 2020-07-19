package com.juawapps.whatstowatch.movies.ui.shared

import com.juawapps.whatstowatch.R

object MovieUiItemFormatter {
    fun formatVoteAverage(voteAverage: Float) = String.format("%.1f", voteAverage)
    fun getVoteColorAttr(voteAverage: Float): Int {
        return if (voteAverage >= 7.0) R.attr.positiveColor else R.attr.mehColor
    }
}