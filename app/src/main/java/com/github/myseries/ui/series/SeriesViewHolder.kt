package com.github.myseries.ui.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.myseries.data.model.Show
import com.github.myseries.databinding.ItemSeriesBinding

class SeriesViewHolder(private val binding: ItemSeriesBinding)  : RecyclerView.ViewHolder(binding.root){

    companion object {
        fun create(parent: ViewGroup): SeriesViewHolder {
            return SeriesViewHolder(
                ItemSeriesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }
    }

    fun bind(show: Show) {
        binding.name.text =show.name ?: "-"

        Glide
            .with(binding.root)
            .load(show.image?.medium)
            .centerCrop()
            .into(binding.poster);
    }
}