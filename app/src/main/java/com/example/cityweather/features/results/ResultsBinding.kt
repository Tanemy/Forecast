package com.example.cityweather.features.results

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cityweather.data.model.WeatherDetail

object ResultsBinding {

    @JvmStatic
    @BindingAdapter("app:items")
    fun setItems(listView: RecyclerView, items: List<WeatherDetail>?) {
        items?.let {
            (listView.adapter as ResultsAdapter).submitList(items)
        }
    }
}
