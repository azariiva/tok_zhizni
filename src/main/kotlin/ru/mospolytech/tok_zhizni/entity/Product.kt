package ru.mospolytech.tok_zhizni.entity

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty

data class ProductDescription(
    @param:[JsonAlias("Состав продукта", "Состав")]
    @get:JsonProperty
    val composition: String? = null,
    @param:[JsonAlias("Стандарт упаковки", "Упаковка")]
    val packaging: String? = null,
    @param:JsonAlias("Срок годности")
    val shelfLife: String? = null,
    @param:JsonAlias("Способ хранения")
    val storageMethod: String? = null,
    @param:JsonAlias("Способ применения")
    val usage: String? = null
)

data class Product(
    val id: Long,
    val article: String,
    val name: String,
    val price: Int,
    val discount: Int,
    val manufacturer: Manufacturer,
    val pharmaceuticalForm: PharmaceuticalForm,
    val series: List<Series>,
    val description: ProductDescription?,
    val imagePath: String?
)

data class ProductCreateRequest(
    val article: String,
    val name: String,
    val price: Int,
    val discount: Int? = null,
    val manufacturerId: Long,
    val pharmaceuticalFormId: Long,
    val seriesIds: List<Long>,
    val description: ProductDescription? = null,
    val imagePath: String? = null
)

data class ProductUpdateRequest(
    val article: String? = null,
    val name: String? = null,
    val price: Int? = null,
    val discount: Int? = null,
    val manufacturerId: Long? = null,
    val pharmaceuticalFormId: Long? = null,
    val seriesIds: List<Long>? = null,
    val description: ProductDescription? = null,
    val imagePath: String? = null
)
