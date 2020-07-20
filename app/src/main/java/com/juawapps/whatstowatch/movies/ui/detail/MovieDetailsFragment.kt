package com.juawapps.whatstowatch.movies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.ui.observeEffects
import com.juawapps.whatstowatch.databinding.FragmentMovieDetailsBinding
import kotlinx.android.synthetic.main.fragment_discover_movies.*
import javax.inject.Inject

class MovieDetailsFragment @Inject constructor(
    private val viewModelFactory: MovieDetailsViewModel.Factory
) : Fragment() {

    private val fragmentArgs: MovieDetailsFragmentArgs by navArgs()
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels {
        viewModelFactory.create(fragmentArgs.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentMovieDetailsBinding.inflate(inflater, container, false).run {
        lifecycleOwner = viewLifecycleOwner
        viewActions = movieDetailsViewModel
        viewModel = movieDetailsViewModel
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar(topAppBar)

        observeEffects(movieDetailsViewModel) {
            when (this) {
                is MovieDetailsViewEffect.ShowErrorMessage -> showErrorSnackBar()
            }
        }
    }

    private fun setUpToolbar(toolbar: Toolbar) {
        val navController = findNavController()
        toolbar.setupWithNavController(navController)
    }

    private fun showErrorSnackBar() {
        Snackbar.make(topAppBar, R.string.error_message, BaseTransientBottomBar.LENGTH_SHORT).show()
    }
}