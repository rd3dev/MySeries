package com.github.myseries.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Schedule (
	val time : String?,
	val days : List<String>?
)