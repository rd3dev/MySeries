package com.github.myseries.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Links(
	val self: Self?,
	val previousEpisode: PreviousEpisode?
)