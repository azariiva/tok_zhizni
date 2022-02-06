package ru.mospolytech.tok_zhizni.repository

import ru.mospolytech.tok_zhizni.entity.Manufacturer
import ru.mospolytech.tok_zhizni.entity.ManufacturerCreateRequest
import ru.mospolytech.tok_zhizni.entity.ManufacturerUpdateRequest

interface ManufacturersRepository {
    fun find(): List<Manufacturer>
    fun find(id: Long): Manufacturer?
    fun create(createRequest: ManufacturerCreateRequest): Manufacturer
    fun update(id: Long, updateRequest: ManufacturerUpdateRequest)
    fun delete(id: Long)
}