package com.example.cityweather.features.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cityweather.databinding.FragmentSearchBinding
import com.example.cityweather.data.model.WeatherDetail
import com.example.cityweather.util.navigation.EventObserver
import com.example.cityweather.util.hideActionBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel:SearchViewModel by viewModels()
    private lateinit var searchBinding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchBinding = FragmentSearchBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewModel = searchViewModel
        }
        return searchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).hideActionBar()

        searchBinding.lifecycleOwner = this.viewLifecycleOwner

        setUpCitySearch()
        setUpNavigationEvents()
        setUpProgressBar()
    }

    private fun setUpCitySearch() {
        searchViewModel.cityName.observe(viewLifecycleOwner) {
            searchViewModel.validateInput()
        }
    }

    private fun setUpNavigationEvents() {
        searchViewModel.errorEvent.observe(viewLifecycleOwner, EventObserver {
            Timber.e("Error occurred: ${getString(it)}")
            Snackbar.make(requireView(), getString(it), Snackbar.LENGTH_LONG).show()
        })
        searchViewModel.navigateToResults.observe(viewLifecycleOwner, EventObserver {
            navigateToResults(it)
        })
    }

    private fun setUpProgressBar() {
        searchViewModel.progressVisible.observe(viewLifecycleOwner) {
            showHideProgress(it)
        }
    }

    private fun navigateToResults(results: List<WeatherDetail>) {
        Timber.d("Received data: $results")
        findNavController().navigate(
            SearchFragmentDirections
                .actionSearchFragmentToResultsFragment(
                    results.first().city,
                    results.toTypedArray()
                )
        )
    }

    private fun showHideProgress(isVisible: Boolean) {
        searchBinding.progress.visibility = if(isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}