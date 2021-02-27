package com.github.myseries.ui.series

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class SeriesLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<SeriesLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: SeriesLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): SeriesLoadStateViewHolder {
        return SeriesLoadStateViewHolder.create(parent, retry)
    }
}