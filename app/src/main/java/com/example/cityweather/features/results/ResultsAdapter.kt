package com.example.cityweather.features.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cityweather.data.model.WeatherDetail
import com.example.cityweather.databinding.DetailItemBinding
import com.example.cityweather.features.results.ResultsAdapter.ViewHolder

class ResultsAdapter(private val resultsViewModel: ResultsViewModel) :
    ListAdapter<WeatherDetail, ViewHolder>(WeatherDetailDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(resultsViewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: DetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: ResultsViewModel, item: WeatherDetail) {
            binding.viewModel = viewModel
            binding.weatherDetail = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DetailItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class WeatherDetailDiffCallback : DiffUtil.ItemCallback<WeatherDetail>() {
    override fun areItemsTheSame(oldItem: WeatherDetail, newItem: WeatherDetail): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WeatherDetail, newItem: WeatherDetail): Boolean {
        return oldItem == newItem
    }
}
