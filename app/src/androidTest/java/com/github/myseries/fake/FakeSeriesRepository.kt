package com.github.myseries.fake

import androidx.paging.PagingData
import com.github.myseries.domain.SeriesRepository
import com.github.myseries.domain.model.Series
import kotlinx.coroutines.flow.Flow

class FakeSeriesRepository : SeriesRepository {
    override fun getSeriesStream(): Flow<PagingData<Series>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesByName(name: String): List<Series> {
        return listOf(Series("Chicken girls", "627433.jpg"))
    }
}