package ru.mospolytech.tok_zhizni.service

import org.springframework.stereotype.Service
import ru.mospolytech.tok_zhizni.db.entity.*
import ru.mospolytech.tok_zhizni.db.repository.ManufacturersRepository
import ru.mospolytech.tok_zhizni.db.repository.PharmaceuticalFormsRepository
import ru.mospolytech.tok_zhizni.service.exception.ManufacturerNotFound
import ru.mospolytech.tok_zhizni.service.exception.PharmaceuticalFormNotFound

@Suppress("SpellCheckingInspection")
@Service
class TokZhizniServiceImpl(
    private val manufacturersRepository: ManufacturersRepository,
    private val pharmaceuticalFormsRepository: PharmaceuticalFormsRepository
) : TokZhizniService {
    override fun addManufacturer(createRequest: ManufacturerCreateRequest): Manufacturer =
        manufacturersRepository.create(createRequest)

    override fun updateManufacturer(id: Long, updateRequest: ManufacturerUpdateRequest) {
        manufacturersRepository.find(id) ?: throw ManufacturerNotFound()
        manufacturersRepository.update(id, updateRequest)
    }

    override fun getAllManufacturers(): List<Manufacturer> =
        manufacturersRepository.find()

    override fun deleteManufacturer(id: Long) {
        manufacturersRepository.find(id) ?: throw ManufacturerNotFound()
        manufacturersRepository.delete(id)
    }

    override fun addPharmaceuticalForm(createRequest: PharmaceuticalFormCreateRequest): PharmaceuticalForm =
        pharmaceuticalFormsRepository.create(createRequest)

    override fun updatePharmaceuticalForm(id: Long, updateRequest: PharmaceuticalFormUpdateRequest) {
        pharmaceuticalFormsRepository.find(id) ?: throw PharmaceuticalFormNotFound()
        pharmaceuticalFormsRepository.update(id, updateRequest)
    }

    override fun getAllPharmaceuticalForms(): List<PharmaceuticalForm> =
        pharmaceuticalFormsRepository.find()

    override fun deletePharmaceuticalForm(id: Long) {
        pharmaceuticalFormsRepository.find(id) ?: throw PharmaceuticalFormNotFound()
        pharmaceuticalFormsRepository.delete(id)
    }
}