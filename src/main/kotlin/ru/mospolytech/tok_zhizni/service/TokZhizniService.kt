package ru.mospolytech.tok_zhizni.service

import ru.mospolytech.tok_zhizni.db.entity.Manufacturer
import ru.mospolytech.tok_zhizni.db.entity.ManufacturerCreateRequest
import ru.mospolytech.tok_zhizni.db.entity.ManufacturerUpdateRequest


@Suppress("SpellCheckingInspection")
interface TokZhizniService {
    fun addManufacturer(createRequest: ManufacturerCreateRequest): Manufacturer
    fun updateManufacturer(id: Long, updateRequest: ManufacturerUpdateRequest)
    fun getAllManufacturers(): List<Manufacturer>
}