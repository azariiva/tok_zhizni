package ru.mospolytech.tok_zhizni.entity.domain

import ru.mospolytech.tok_zhizni.entity.dto.SeriesCreateRequestDto
import ru.mospolytech.tok_zhizni.entity.dto.SeriesUpdateRequestDto

data class Series(
    val id: Long,
    val name: String
)

data class SeriesCreateRequest(
    val name: String
) {
    constructor(dto: SeriesCreateRequestDto): this(dto.name!!)
}

data class SeriesUpdateRequest(
    val name: String?
) {
    constructor(dto: SeriesUpdateRequestDto): this(dto.name)
}