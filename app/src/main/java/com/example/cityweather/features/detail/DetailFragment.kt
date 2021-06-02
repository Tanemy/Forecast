package com.example.cityweather.features.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.cityweather.R
import com.example.cityweather.databinding.FragmentDetailBinding
import com.example.cityweather.databinding.FragmentResultsBinding
import com.example.cityweather.features.results.ResultsFragmentArgs

class DetailFragment : Fragment() {

    private val arguments by navArgs<DetailFragmentArgs>()
    private lateinit var detailBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailBinding = FragmentDetailBinding.inflate(
            inflater,
            container,
            false
        )
        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailBinding.lifecycleOwner = this.viewLifecycleOwner

        detailBinding.detail = arguments.detail
    }
}