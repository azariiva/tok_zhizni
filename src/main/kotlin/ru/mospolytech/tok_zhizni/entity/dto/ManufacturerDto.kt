package ru.mospolytech.tok_zhizni.entity.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ru.mospolytech.tok_zhizni.entity.domain.Manufacturer
import javax.validation.constraints.NotEmpty

data class ManufacturerDto(
    val id: Long,
    val name: String
)

data class ManufacturerCreateRequestDto(
    @param:[NotEmpty JsonProperty(required = true)]
    val name: String?
)

data class ManufacturerUpdateRequestDto(
    val name: String?
)

fun Manufacturer.toDto(): ManufacturerDto =
    ManufacturerDto(
        id = id,
        name = name
    )