package com.github.myseries.ui.series.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.myseries.domain.ShowRepository

class SearchViewModelFactory(private val repository: ShowRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }