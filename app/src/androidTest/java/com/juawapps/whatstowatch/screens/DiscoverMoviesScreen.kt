package com.juawapps.whatstowatch.screens

import android.view.View
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.juawapps.whatstowatch.R
import org.hamcrest.Matcher

class DiscoverMoviesScreen : Screen<DiscoverMoviesScreen>() {
    val movies: KRecyclerView = KRecyclerView({
        withId(R.id.list)
    }, itemTypeBuilder = {
        itemType(::MovieListItem)
    })

    class MovieListItem(parent: Matcher<View>) : KRecyclerItem<MovieListItem>(parent) {
        val image: KImageView = KImageView(parent) { withId(R.id.image) }
        val title: KTextView = KTextView(parent) { withId(R.id.title) }
        val year: KTextView = KTextView(parent) { withId(R.id.year) }
        val language: KTextView = KTextView(parent) { withId(R.id.language) }
        val voteAverage: KTextView = KTextView(parent) { withId(R.id.voteAverage) }
    }
}
