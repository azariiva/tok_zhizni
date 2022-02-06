package ru.mospolytech.tok_zhizni.service

import ru.mospolytech.tok_zhizni.db.entity.*


@Suppress("SpellCheckingInspection")
interface TokZhizniService {
    fun addManufacturer(createRequest: ManufacturerCreateRequest): Manufacturer
    fun updateManufacturer(id: Long, updateRequest: ManufacturerUpdateRequest)
    fun getAllManufacturers(): List<Manufacturer>
    fun deleteManufacturer(id: Long)

    fun addPharmaceuticalForm(createRequest: PharmaceuticalFormCreateRequest): PharmaceuticalForm
    fun updatePharmaceuticalForm(id: Long, updateRequest: PharmaceuticalFormUpdateRequest)
    fun getAllPharmaceuticalForms(): List<PharmaceuticalForm>
    fun deletePharmaceuticalForm(id: Long)

    fun addSeries(createRequest: SeriesCreateRequest): Series
    fun updateSeries(id: Long, updateRequest: SeriesUpdateRequest)
    fun getAllSeries(): List<Series>
    fun deleteSeries(id: Long)
}