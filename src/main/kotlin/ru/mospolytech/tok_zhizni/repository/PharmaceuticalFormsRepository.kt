package ru.mospolytech.tok_zhizni.repository

import ru.mospolytech.tok_zhizni.entity.PharmaceuticalForm
import ru.mospolytech.tok_zhizni.entity.PharmaceuticalFormCreateRequest
import ru.mospolytech.tok_zhizni.entity.PharmaceuticalFormUpdateRequest

interface PharmaceuticalFormsRepository {
    fun find(): List<PharmaceuticalForm>
    fun find(id: Long): PharmaceuticalForm?
    fun create(createRequest: PharmaceuticalFormCreateRequest): PharmaceuticalForm
    fun update(id: Long, updateRequest: PharmaceuticalFormUpdateRequest)
    fun delete(id: Long)
}