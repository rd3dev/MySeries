package com.github.myseries.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Show (
	val id : Int?,
	val url : String?,
	val name : String?,
	val type : String?,
	val language : String?,
	val genres : List<String>?,
	val status : String?,
	val runtime : Int?,
	val premiered : String?,
	val officialSite : String?,
	val schedule : Schedule?,
	val rating : Rating?,
	val weight : Int?,
	val network : Network?,
	val webChannel : WebChannel?,
	val externals : Externals?,
	val image : Image?,
	val summary : String?,
	val updated : Int?,
	val _links : Links?
)