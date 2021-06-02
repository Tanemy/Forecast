package com.example.cityweather.util

import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun AppCompatActivity.hideActionBar() = this.supportActionBar?.hide()

fun AppCompatActivity.showActionBar() = this.supportActionBar?.show()

fun RecyclerView.itemDivider(@DrawableRes dividerResource: Int) {
    val divider = DividerItemDecoration(
        this.context,
        DividerItemDecoration.VERTICAL
    )
    ContextCompat.getDrawable(this.context, dividerResource)?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}