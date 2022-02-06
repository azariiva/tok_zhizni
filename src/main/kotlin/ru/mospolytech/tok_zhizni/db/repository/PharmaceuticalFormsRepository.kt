package ru.mospolytech.tok_zhizni.db.repository

import ru.mospolytech.tok_zhizni.db.entity.PharmaceuticalForm
import ru.mospolytech.tok_zhizni.db.entity.PharmaceuticalFormCreateRequest
import ru.mospolytech.tok_zhizni.db.entity.PharmaceuticalFormUpdateRequest

interface PharmaceuticalFormsRepository {
    fun find(): List<PharmaceuticalForm>
    fun find(id: Long): PharmaceuticalForm?
    fun create(createRequest: PharmaceuticalFormCreateRequest): PharmaceuticalForm
    fun update(id: Long, updateRequest: PharmaceuticalFormUpdateRequest)
    fun delete(id: Long)
}