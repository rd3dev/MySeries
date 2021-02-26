package com.github.myseries.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.myseries.domain.SeriesRepository
import com.github.myseries.domain.model.Series
import kotlinx.coroutines.flow.Flow

class SeriesViewModel(private val repository: SeriesRepository) : ViewModel() {
    fun getSeries(): Flow<PagingData<Series>> {
        return repository.getSeriesStream()
            .cachedIn(viewModelScope)
    }
}
