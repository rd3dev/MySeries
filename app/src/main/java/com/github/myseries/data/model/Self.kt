package com.github.myseries.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Self (
	val href : String?
)