package com.github.myseries.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.myseries.data.ShowPagingSource
import com.github.myseries.data.model.Show
import com.github.myseries.data.remote.TVMazeService
import kotlinx.coroutines.flow.Flow

class ShowRepository(private val service: TVMazeService) {
    fun getShowsStream(): Flow<PagingData<Show>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ShowPagingSource(service) }
        ).flow
    }

    suspend fun getShowsByNameStream(name: String): List<Show> {
        return service.getShowsByName(name)
    }

    companion object {
        private const val PAGE_SIZE = 50
    }
}