package com.juawapps.whatstowatch.movies.ui.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.ui.BindingListAdapter
import com.juawapps.whatstowatch.databinding.MovieListItemBinding

class MoviesListAdapter(
    private val viewActions: DiscoverMoviesViewActions
) : BindingListAdapter<MovieListUiItem, MovieListItemBinding>(RecipeSearchDiff()) {

    override fun bind(
        binding: MovieListItemBinding,
        item: MovieListUiItem,
        position: Int,
        payloads: MutableList<Any>
    ) {

        binding.item = item
        binding.viewActions = viewActions
        binding.image.tag = item
    }

    override fun createBinding(parent: ViewGroup): MovieListItemBinding {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DataBindingUtil.inflate(
            layoutInflater,
            R.layout.movie_list_item,
            parent,
            false
        )
    }

    class RecipeSearchDiff : DiffUtil.ItemCallback<MovieListUiItem>() {
        override fun areItemsTheSame(
            oldItem: MovieListUiItem,
            newItem: MovieListUiItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieListUiItem,
            newItem: MovieListUiItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}
