package ru.mospolytech.tok_zhizni.entity.domain

import ru.mospolytech.tok_zhizni.entity.dto.PharmaceuticalFormCreateRequestDto
import ru.mospolytech.tok_zhizni.entity.dto.PharmaceuticalFormUpdateRequestDto

data class PharmaceuticalForm(
    val id: Long,
    val name: String
)

data class PharmaceuticalFormCreateRequest(
    val name: String
) {
    constructor(dto: PharmaceuticalFormCreateRequestDto): this(dto.name!!)
}

data class PharmaceuticalFormUpdateRequest(
    val name: String? = null
) {
    constructor(dto: PharmaceuticalFormUpdateRequestDto): this(dto.name)
}

