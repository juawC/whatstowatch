package com.juawapps.whatstowatch.screens

import com.agoda.kakao.image.KImageView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.juawapps.whatstowatch.R

class MoviesDetailsScreen : Screen<MoviesDetailsScreen>() {
    val title = KTextView { withId(R.id.title) }
    val image = KImageView { withId(R.id.image) }
    val year = KTextView { withId(R.id.year) }
    val language = KTextView { withId(R.id.language) }
    val voteAverage = KTextView { withId(R.id.voteAverage) }
    val votes = KTextView { withId(R.id.votes) }
    val overview = KTextView { withId(R.id.overview) }
    val overviewText = KTextView { withId(R.id.overviewText) }
}
