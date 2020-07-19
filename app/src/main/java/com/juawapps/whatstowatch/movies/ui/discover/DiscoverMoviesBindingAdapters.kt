package com.juawapps.whatstowatch.movies.ui.discover

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("setMovies")
fun setPosts(recyclerView: RecyclerView, list: List<MovieListUiItem>?) {
    (recyclerView.adapter as MoviesListAdapter).submitList(list)
}