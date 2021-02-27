package com.github.myseries.data.remote

import com.github.myseries.data.model.SearchResponse
import com.github.myseries.data.model.Show
import retrofit2.http.GET
import retrofit2.http.Query

interface TVMazeService {
    @GET("shows")
    suspend fun getShowsByPage(@Query("page")  page: Int): List<Show>

    @GET("search/shows")
    suspend fun getShowsByName(@Query("q") query: String): List<SearchResponse>
}