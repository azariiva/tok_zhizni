package ru.mospolytech.tok_zhizni.repository

import ru.mospolytech.tok_zhizni.entity.domain.Product
import ru.mospolytech.tok_zhizni.entity.domain.ProductCreateRequest
import ru.mospolytech.tok_zhizni.entity.domain.ProductUpdateRequest

interface ProductsRepository : CrudRepository<ProductCreateRequest, ProductUpdateRequest, Product>