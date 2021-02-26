package com.github.myseries.ui.series

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.github.myseries.domain.model.Series
import com.github.myseries.ui.common.SeriesViewHolder

class SeriesAdapter :
    PagingDataAdapter<Series, SeriesViewHolder>(SHOW_COMPARATOR) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SeriesViewHolder {
        return SeriesViewHolder.create(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: SeriesViewHolder, position: Int) {
        getItem(position)?.let {
            viewHolder.bind(it)
        }
    }

    companion object {
        private val SHOW_COMPARATOR = object : DiffUtil.ItemCallback<Series>() {
            override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean =
                oldItem.poster == newItem.poster

            override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean =
                oldItem == newItem
        }
    }
}
