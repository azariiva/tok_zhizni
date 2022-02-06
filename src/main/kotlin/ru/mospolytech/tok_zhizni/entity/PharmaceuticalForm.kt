package ru.mospolytech.tok_zhizni.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class PharmaceuticalForm(
    val id: Long,
    val name: String
)

data class PharmaceuticalFormCreateRequest(
    @param:JsonProperty(required = true)
    val name: String
)

data class PharmaceuticalFormUpdateRequest(
    val name: String? = null
)

