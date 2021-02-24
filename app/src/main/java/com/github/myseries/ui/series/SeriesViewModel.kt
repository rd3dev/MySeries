package com.github.myseries.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.myseries.data.model.Show
import com.github.myseries.domain.ShowRepository
import kotlinx.coroutines.flow.Flow

class SeriesViewModel(private val repository: ShowRepository) : ViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Show>>? = null

    fun getSeries(): Flow<PagingData<Show>> {
        val lastResult = currentSearchResult

        val newResult: Flow<PagingData<Show>> = repository.getShowsStream()
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}