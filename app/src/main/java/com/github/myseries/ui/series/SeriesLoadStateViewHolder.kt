package com.github.myseries.ui.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.github.myseries.R
import com.github.myseries.databinding.ItemSeriesLoadStateFooterBinding

class SeriesLoadStateViewHolder(
    private val binding: ItemSeriesLoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): SeriesLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_series_load_state_footer, parent, false)
            val binding = ItemSeriesLoadStateFooterBinding.bind(view)
            return SeriesLoadStateViewHolder(binding, retry)
        }
    }
}