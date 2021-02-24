package com.github.myseries.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Externals(
    val tvrage: Int?,
    val thetvdb: Int?,
    val imdb: String?
)