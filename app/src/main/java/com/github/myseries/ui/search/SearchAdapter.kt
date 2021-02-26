package com.github.myseries.ui.search

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.myseries.domain.model.Series
import com.github.myseries.ui.common.SeriesViewHolder

class SearchAdapter :
    RecyclerView.Adapter<SeriesViewHolder>() {

    private var shows = listOf<Series>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SeriesViewHolder {
        return SeriesViewHolder.create(viewGroup)
    }

    fun setShows(list: List<Series>) {
        shows = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: SeriesViewHolder, position: Int) {
        shows[position].let {
            Log.w("Adapter", it.toString())
            viewHolder.bind(it)
        }
    }

    override fun getItemCount(): Int {
       return shows.size
    }
}
