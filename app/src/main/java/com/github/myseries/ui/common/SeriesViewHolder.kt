package com.github.myseries.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.myseries.databinding.ItemSeriesBinding
import com.github.myseries.domain.model.Series

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

    fun bind(series: Series) {
        binding.name.text =series.name ?: "-"

        Glide
            .with(binding.root)
            .load(series.poster)
            .centerCrop()
            .into(binding.poster);
    }
}