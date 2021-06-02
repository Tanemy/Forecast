package com.example.cityweather.features.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cityweather.R
import com.example.cityweather.data.model.WeatherDetail
import com.example.cityweather.databinding.FragmentResultsBinding
import com.example.cityweather.util.itemDivider
import com.example.cityweather.util.navigation.EventObserver
import com.example.cityweather.util.showActionBar
import timber.log.Timber

class ResultsFragment : Fragment() {

    private val resultsViewModel: ResultsViewModel by viewModels()
    private val arguments by navArgs<ResultsFragmentArgs>()

    private lateinit var resultsBinding: FragmentResultsBinding
    private lateinit var resultsAdapter: ResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        resultsBinding = FragmentResultsBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewModel = resultsViewModel
        }
        return resultsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).showActionBar()

        resultsBinding.lifecycleOwner = this.viewLifecycleOwner

        setUpResultsAdapter()
        setUpNavigation()

        resultsViewModel.setResults(arguments.results)
    }

    private fun setUpResultsAdapter() {
        val viewModel = resultsBinding.viewModel
        if (viewModel != null) {
            resultsAdapter = ResultsAdapter(viewModel)
            resultsBinding.resultsList.itemDivider(R.drawable.item_divider)
            resultsBinding.resultsList.adapter = resultsAdapter
        } else {
            Timber.w("ViewModel not initialized when setting up adapter.")
        }
    }

    private fun setUpNavigation() {
        resultsViewModel.navigateToDetail.observe(
            viewLifecycleOwner,
            EventObserver {
                navigateToDetail(it)
            }
        )
    }

    private fun navigateToDetail(detail: WeatherDetail) {
        Timber.d("Clicked detail: $detail")
        findNavController().navigate(
            ResultsFragmentDirections
                .actionResultsFragmentToDetailFragment(
                    arguments.city,
                    detail
                )
        )
    }
}