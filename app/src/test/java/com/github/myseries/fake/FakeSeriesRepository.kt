package com.github.myseries.fake

import androidx.paging.PagingData
import com.github.myseries.domain.SeriesRepository
import com.github.myseries.domain.model.Series
import kotlinx.coroutines.flow.Flow

class FakeSeriesRepository : SeriesRepository {
    var result: Any = Unit

    override fun getSeriesStream(): Flow<PagingData<Series>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesByName(name: String): List<Series> {
        return if (name.contains("girl")) {
            listOf(Series("Chicken girls", "627433.jpg")).also {
                result = it
            }
        } else {
            when (result) {
                is List<*> -> {
                    result as List<Series>
                }
                is Exception -> {
                    throw result as Exception
                }
                else -> {
                    throw UnsupportedOperationException()
                }
            }
        }

    }
}