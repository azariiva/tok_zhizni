package ru.mospolytech.tok_zhizni.db.repository

import ru.mospolytech.tok_zhizni.db.entity.Product
import ru.mospolytech.tok_zhizni.db.entity.ProductCreateRequest
import ru.mospolytech.tok_zhizni.db.entity.ProductUpdateRequest

interface ProductRepository {
    fun find(): List<Product>
    fun find(id: Long): Product?
    fun create(createRequest: ProductCreateRequest): Product
    fun update(id: Long, updateRequest: ProductUpdateRequest)
    fun delete(id: Long)
}