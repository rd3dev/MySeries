package com.github.myseries.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.myseries.data.model.Show
import com.github.myseries.domain.ShowRepository
import kotlinx.coroutines.flow.Flow

class SeriesViewModel(private val repository: ShowRepository) : ViewModel() {
    fun getSeries(): Flow<PagingData<Show>> {
        return repository.getShowsStream()
            .cachedIn(viewModelScope)
    }
}
