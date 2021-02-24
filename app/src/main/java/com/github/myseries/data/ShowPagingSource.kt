package com.github.myseries.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.myseries.data.model.Show
import com.github.myseries.data.remote.TVMazeService
import retrofit2.HttpException
import java.io.IOException

private const val TVMAZE_STARTING_PAGE_INDEX = 0

class ShowPagingSource(
    private val service: TVMazeService,
) : PagingSource<Int, Show>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Show> {
        val page = params.key ?: TVMAZE_STARTING_PAGE_INDEX
        return try {
            val shows = service.listShowsByPage(page)
            val nextKey = if (shows.isEmpty()) {
                null
            } else {
                page + 1
            }

            LoadResult.Page(
                data = shows,
                prevKey = if (page == TVMAZE_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Show>): Int? {
        return 0 //TODO
    }
}