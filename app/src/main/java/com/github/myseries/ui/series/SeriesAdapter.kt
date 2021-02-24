package com.github.myseries.ui.series

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.myseries.R
import com.github.myseries.data.model.Show

class SeriesAdapter() :
    PagingDataAdapter<Show, SeriesAdapter.ViewHolder>(SHOW_COMPARATOR) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val poster: ImageView = view.findViewById(R.id.poster)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_series, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.name.text =item?.name ?: "-"

        Glide
            .with(viewHolder.itemView)
            .load(item?.image?.medium)
            .centerCrop()
            .into(viewHolder.poster);
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
