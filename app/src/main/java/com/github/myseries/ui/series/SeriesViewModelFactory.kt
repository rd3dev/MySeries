package com.github.myseries.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.myseries.domain.SeriesRepository

class SeriesViewModelFactory(private val repository: SeriesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SeriesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SeriesViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }