package ru.mospolytech.tok_zhizni.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class Manufacturer(
    val id: Long,
    val name: String
)

data class ManufacturerCreateRequest(
    @param:JsonProperty(required = true)
    val name: String
)

data class ManufacturerUpdateRequest(
    val name: String? = null
)