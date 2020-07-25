package com.juawapps.whatstowatch.movies.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.ui.DefaultListAdapter
import com.juawapps.whatstowatch.common.ui.observeEffects
import com.juawapps.whatstowatch.databinding.FragmentDiscoverMoviesBinding
import com.juawapps.whatstowatch.databinding.MovieListItemBinding
import com.juawapps.whatstowatch.di.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_discover_movies.*
import javax.inject.Inject

class DiscoverMoviesFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory<DiscoverMoviesViewModel>
) : Fragment() {

    private val discoverMoviesViewModel: DiscoverMoviesViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentDiscoverMoviesBinding.inflate(inflater, container, false).apply {
        bindView(discoverMoviesViewModel)
        observeEffects(discoverMoviesViewModel, ::viewEffectsActions)
    }.root

    private fun FragmentDiscoverMoviesBinding.bindView(
        discoverMoviesViewModel: DiscoverMoviesViewModel
    ) {
        lifecycleOwner = viewLifecycleOwner
        viewActions = discoverMoviesViewModel
        viewModel = discoverMoviesViewModel
        list.adapter = DefaultListAdapter<MovieListUiItem, Long, MovieListItemBinding>(
            R.layout.movie_list_item,
            bindView = { movieListItem ->
                item = movieListItem
                viewActions = discoverMoviesViewModel
            },
            itemIdentifier = MovieListUiItem::id,
            haveContentsChanged = MovieListUiItem::equals
        )
    }

    private fun viewEffectsActions(effect: DiscoverMoviesViewEffect) {
        when (effect) {
            is DiscoverMoviesViewEffect.ShowErrorMessage -> showErrorSnackBar()
            is DiscoverMoviesViewEffect.NavigateToDetail -> navigateToDetail(effect.movieId)
        }
    }

    private fun navigateToDetail(movieId: Long) {
        val action =
            DiscoverMoviesFragmentDirections.actionDiscoverMoviesFragmentToMovieDetailsFragment(
                movieId
            )

        findNavController().navigate(action)
    }

    private fun showErrorSnackBar() {
        Snackbar.make(list, R.string.error_message, LENGTH_SHORT).show()
    }
}