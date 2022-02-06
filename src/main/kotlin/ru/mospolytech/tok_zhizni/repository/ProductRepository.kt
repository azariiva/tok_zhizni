package ru.mospolytech.tok_zhizni.repository

import ru.mospolytech.tok_zhizni.entity.*

interface ProductRepository {
    fun find(): List<Product>
    fun find(id: Long): Product?
    fun create(
        createRequest: ProductCreateRequest,
        series: List<Series>,
        manufacturer: Manufacturer,
        pharmaceuticalForm: PharmaceuticalForm
    ): Product
    fun update(id: Long, updateRequest: ProductUpdateRequest)
    fun delete(id: Long)
}