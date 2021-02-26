package com.github.myseries.domain

import androidx.paging.PagingData
import com.github.myseries.domain.model.Series
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    fun getSeriesStream(): Flow<PagingData<Series>>
    suspend fun getSeriesByName(name: String): List<Series>
}