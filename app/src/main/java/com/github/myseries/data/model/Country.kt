package com.github.myseries.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Country (
	val name : String?,
	val code : String,
	val timezone : String?
)