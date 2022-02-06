package ru.mospolytech.tok_zhizni.db.repository

import ru.mospolytech.tok_zhizni.db.entity.*

interface ProductRepository {
    fun find(): List<Product>
    fun find(id: Long): Product?
    fun create(createRequest: ProductCreateRequest, manufacturer: Manufacturer, pharmaceuticalForm: PharmaceuticalForm): Product
    fun update(id: Long, updateRequest: ProductUpdateRequest)
    fun delete(id: Long)
}