package ru.mospolytech.tok_zhizni.entity.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ru.mospolytech.tok_zhizni.entity.domain.Series
import javax.validation.constraints.NotNull

data class SeriesDto(
    val id: Long,
    val name: String
)

data class SeriesCreateRequestDto(
    @param:[NotNull JsonProperty(required=true)]
    val name: String?
)

data class SeriesUpdateRequestDto(
    val name: String?
)

fun Series.toDto(): SeriesDto =
    SeriesDto(
        id = id,
        name = name
    )