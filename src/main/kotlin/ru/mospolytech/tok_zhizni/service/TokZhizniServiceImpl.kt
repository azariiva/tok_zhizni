package ru.mospolytech.tok_zhizni.service

import org.springframework.stereotype.Service
import ru.mospolytech.tok_zhizni.db.entity.Manufacturer
import ru.mospolytech.tok_zhizni.db.entity.ManufacturerCreateRequest
import ru.mospolytech.tok_zhizni.db.entity.ManufacturerUpdateRequest
import ru.mospolytech.tok_zhizni.db.repository.ManufacturersRepository
import ru.mospolytech.tok_zhizni.service.exception.EntityNotFound

@Suppress("SpellCheckingInspection")
@Service
class TokZhizniServiceImpl(
    private val manufacturersRepository: ManufacturersRepository
) : TokZhizniService {
    override fun addManufacturer(createRequest: ManufacturerCreateRequest): Manufacturer =
        manufacturersRepository.create(createRequest)

    override fun updateManufacturer(id: Long, updateRequest: ManufacturerUpdateRequest) {
        manufacturersRepository.find(id) ?: throw EntityNotFound()
        manufacturersRepository.update(id, updateRequest)
    }

    override fun getAllManufacturers(): List<Manufacturer> =
        manufacturersRepository.find()
}