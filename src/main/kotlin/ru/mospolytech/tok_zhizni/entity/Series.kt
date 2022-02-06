package ru.mospolytech.tok_zhizni.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class Series(
    val id: Long,
    val name: String
)

data class SeriesCreateRequest(
    @param:JsonProperty(required=true)
    val name: String
)

data class SeriesUpdateRequest(
    val name: String? = null
)
