package ru.mospolytech.tok_zhizni.entity.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import ru.mospolytech.tok_zhizni.entity.domain.Product
import ru.mospolytech.tok_zhizni.entity.domain.ProductDescription

data class ProductDescriptionDto(
    @param:[JsonAlias("Состав продукта", "Состав")]
    @get:JsonProperty
    val composition: String?,
    @param:[JsonAlias("Стандарт упаковки", "Упаковка")]
    val packaging: String?,
    @param:JsonAlias("Срок годности")
    val shelfLife: String?,
    @param:JsonAlias("Способ хранения")
    val storageMethod: String?,
    @param:JsonAlias("Способ применения")
    val usage: String?
)

data class ProductDto(
    val id: Long,
    val article: String,
    val name: String,
    val price: Int,
    val discount: Int,
    val manufacturer: ManufacturerDto,
    val pharmaceuticalForm: PharmaceuticalFormDto,
    val series: List<SeriesDto>,
    val description: ProductDescriptionDto?,
    val imagePath: String?
)

data class ProductCreateRequestDto(
    val article: String,
    val name: String,
    val price: Int,
    val discount: Int?,
    val manufacturerId: Long,
    val pharmaceuticalFormId: Long,
    val seriesIds: List<Long>,
    val description: ProductDescriptionDto?,
    val imagePath: String?
)

data class ProductUpdateRequestDto(
    val article: String?,
    val name: String?,
    val price: Int?,
    val discount: Int?,
    val manufacturerId: Long?,
    val pharmaceuticalFormId: Long?,
    val seriesIds: List<Long>?,
    val description: ProductDescriptionDto?,
    val imagePath: String?
)

fun ProductDescription.toDto(): ProductDescriptionDto =
    ProductDescriptionDto(
        composition = composition,
        packaging = packaging,
        shelfLife = shelfLife,
        storageMethod = storageMethod,
        usage = usage
    )

fun Product.toDto(): ProductDto =
    ProductDto(
        id = id,
        article = article,
        name = name,
        price = price,
        discount = discount,
        manufacturer = manufacturer.toDto(),
        pharmaceuticalForm = pharmaceuticalForm.toDto(),
        series = series.map { it.toDto() },
        description = description?.toDto(),
        imagePath = imagePath,
    )