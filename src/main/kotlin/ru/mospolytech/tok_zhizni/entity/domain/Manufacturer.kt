package ru.mospolytech.tok_zhizni.entity.domain

import ru.mospolytech.tok_zhizni.entity.dto.ManufacturerCreateRequestDto
import ru.mospolytech.tok_zhizni.entity.dto.ManufacturerUpdateRequestDto

data class Manufacturer(
    val id: Long,
    val name: String
)

data class ManufacturerCreateRequest(
    val name: String
) {
    constructor(dto: ManufacturerCreateRequestDto): this(dto.name!!)
}

data class ManufacturerUpdateRequest(
    val name: String?
) {
    constructor(dto: ManufacturerUpdateRequestDto): this(dto.name)
}