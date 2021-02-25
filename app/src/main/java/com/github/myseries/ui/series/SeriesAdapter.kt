package com.github.myseries.ui.series

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.github.myseries.data.model.Show

class SeriesAdapter() :
    PagingDataAdapter<Show, SeriesViewHolder>(SHOW_COMPARATOR) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SeriesViewHolder {
        return SeriesViewHolder.create(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: SeriesViewHolder, position: Int) {
        getItem(position)?.let {
            viewHolder.bind(it)
        }
    }

    companion object {
        private val SHOW_COMPARATOR = object : DiffUtil.ItemCallback<Show>() {
            override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean =
                oldItem == newItem
        }
    }
}
