package com.github.myseries.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.myseries.data.ShowPagingSource
import com.github.myseries.data.model.toSeries
import com.github.myseries.data.remote.TVMazeService
import com.github.myseries.domain.model.NetworkException
import com.github.myseries.domain.model.Series
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class RealSeriesRepository(private val service: TVMazeService) : SeriesRepository {
    override fun getSeriesStream(): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ShowPagingSource(service) }
        ).flow
    }

    override suspend fun getSeriesByName(name: String): List<Series> {
        return try {
            service.getShowsByName(name).map {
                it.show.toSeries()
            }
        } catch (exception: IOException) {
            throw NetworkException.Connection
        } catch (exception: retrofit2.HttpException) {
            throw NetworkException.Server
        }
    }

    companion object {
        private const val PAGE_SIZE = 50
    }
}