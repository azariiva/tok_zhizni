package ru.mospolytech.tok_zhizni.service

import org.springframework.stereotype.Service
import ru.mospolytech.tok_zhizni.entity.*
import ru.mospolytech.tok_zhizni.entity.exception.ManufacturerNotFound
import ru.mospolytech.tok_zhizni.entity.exception.PharmaceuticalFormNotFound
import ru.mospolytech.tok_zhizni.entity.exception.ProductNotFound
import ru.mospolytech.tok_zhizni.entity.exception.SeriesNotFound
import ru.mospolytech.tok_zhizni.repository.ManufacturersRepository
import ru.mospolytech.tok_zhizni.repository.PharmaceuticalFormsRepository
import ru.mospolytech.tok_zhizni.repository.ProductRepository
import ru.mospolytech.tok_zhizni.repository.SeriesRepository

@Suppress("SpellCheckingInspection")
@Service
class TokZhizniServiceImpl(
    private val manufacturersRepository: ManufacturersRepository,
    private val pharmaceuticalFormsRepository: PharmaceuticalFormsRepository,
    private val seriesRepository: SeriesRepository,
    private val productRepository: ProductRepository
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

    override fun addSeries(createRequest: SeriesCreateRequest): Series =
        seriesRepository.create(createRequest)

    override fun updateSeries(id: Long, updateRequest: SeriesUpdateRequest) {
        seriesRepository.find(id) ?: throw SeriesNotFound()
        seriesRepository.update(id, updateRequest)
    }

    override fun getAllSeries(): List<Series> =
        seriesRepository.find()

    override fun deleteSeries(id: Long) {
        seriesRepository.find(id)
        seriesRepository.delete(id)
    }

    override fun addProduct(createRequest: ProductCreateRequest): Product {
        val manufacturer = manufacturersRepository.find(createRequest.manufacturerId) ?: throw ManufacturerNotFound()
        val pharmaceuticalForm =
            pharmaceuticalFormsRepository.find(createRequest.pharmaceuticalFormId) ?: throw PharmaceuticalFormNotFound()
        val series = seriesRepository.find(createRequest.seriesIds)
        val illegalSeries = createRequest.seriesIds - series.map { it.id }

        if (illegalSeries.isNotEmpty()) throw SeriesNotFound()

        return productRepository.create(createRequest, series, manufacturer, pharmaceuticalForm)
    }

    override fun updateProduct(id: Long, updateRequest: ProductUpdateRequest) {
        productRepository.find(id) ?: throw ProductNotFound()
        updateRequest.manufacturerId?.let { manufacturersRepository.find(it) ?: throw ManufacturerNotFound() }
        updateRequest.pharmaceuticalFormId?.let {
            pharmaceuticalFormsRepository.find(it) ?: throw PharmaceuticalFormNotFound()
        }
        updateRequest.seriesIds?.let {
            val illegalSeries = it - seriesRepository.find(it).map { ser -> ser.id }
            if (illegalSeries.isNotEmpty()) throw SeriesNotFound()
        }

        productRepository.update(id, updateRequest)
    }

    override fun getProduct(id: Long): Product =
        productRepository.find(id) ?: throw ProductNotFound()

    override fun getAllProducts(): List<Product> =
        productRepository.find()

    override fun deleteProduct(id: Long) {
        productRepository.find(id) ?: throw ProductNotFound()
        productRepository.delete(id)
    }
}