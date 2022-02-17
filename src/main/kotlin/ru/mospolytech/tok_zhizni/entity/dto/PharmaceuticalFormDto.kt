package ru.mospolytech.tok_zhizni.entity.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ru.mospolytech.tok_zhizni.entity.domain.PharmaceuticalForm
import javax.validation.constraints.NotEmpty

data class PharmaceuticalFormDto(
    val id: Long,
    val name: String
)

data class PharmaceuticalFormCreateRequestDto(
    @field:[NotEmpty JsonProperty(required = true)]
    val name: String?
)

data class PharmaceuticalFormUpdateRequestDto(
    val name: String?
)

fun PharmaceuticalForm.toDto(): PharmaceuticalFormDto =
    PharmaceuticalFormDto(
        id = id,
        name = name
    )

