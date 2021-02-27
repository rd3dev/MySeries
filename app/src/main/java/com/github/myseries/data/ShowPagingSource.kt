package com.github.myseries.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.myseries.data.model.toSeries
import com.github.myseries.data.remote.TVMazeService
import com.github.myseries.domain.model.Series
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException

private const val TVMAZE_STARTING_PAGE_INDEX = 0

class ShowPagingSource(
    private val service: TVMazeService,
) : PagingSource<Int, Series>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Series> {
        val page = params.key ?: TVMAZE_STARTING_PAGE_INDEX
        return try {
            val series = service.getShowsByPage(page).map { it.toSeries() }
            val nextKey = if (series.isEmpty()) {
                null
            } else {
                page + 1
            }

            LoadResult.Page(
                data = series,
                prevKey = if (page == TVMAZE_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: JsonDataException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Series>): Int? {
        return state.anchorPosition
    }
}