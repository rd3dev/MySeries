package com.github.myseries.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Network (
	val id : Int?,
	val name : String?,
	val country : Country?
)

