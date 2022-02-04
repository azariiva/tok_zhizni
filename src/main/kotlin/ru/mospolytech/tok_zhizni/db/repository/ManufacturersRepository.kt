package ru.mospolytech.tok_zhizni.db.repository

import ru.mospolytech.tok_zhizni.db.entity.Manufacturer
import ru.mospolytech.tok_zhizni.db.entity.ManufacturerCreateRequest
import ru.mospolytech.tok_zhizni.db.entity.ManufacturerUpdateRequest

interface ManufacturersRepository {
    fun find(): List<Manufacturer>
    fun find(id: Long): Manufacturer?
    fun create(createRequest: ManufacturerCreateRequest): Manufacturer
    fun update(id: Long, updateRequest: ManufacturerUpdateRequest)
}