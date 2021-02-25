package com.github.myseries.data.remote

import com.github.myseries.data.model.Show
import retrofit2.http.GET
import retrofit2.http.Query

interface TVMazeService {
    @GET("shows")
    suspend fun listShowsByPage(@Query("page")  page: Int): List<Show>

    @GET("search/shows")
    suspend fun getShowsByName(@Query("query") query: String): List<Show>
}