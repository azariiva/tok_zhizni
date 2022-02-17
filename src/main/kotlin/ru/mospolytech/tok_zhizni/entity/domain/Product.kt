package ru.mospolytech.tok_zhizni.entity.domain

import ru.mospolytech.tok_zhizni.entity.dto.ProductCreateRequestDto
import ru.mospolytech.tok_zhizni.entity.dto.ProductDescriptionDto
import ru.mospolytech.tok_zhizni.entity.dto.ProductUpdateRequestDto

data class ProductDescription(
    val composition: String?,
    val packaging: String?,
    val shelfLife: String?,
    val storageMethod: String?,
    val usage: String?
) {
    constructor(dto: ProductDescriptionDto) : this(
        composition = dto.composition,
        packaging = dto.packaging,
        shelfLife = dto.shelfLife,
        storageMethod = dto.storageMethod,
        usage = dto.usage
    )
}

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
    val discount: Int?,
    val manufacturer: Manufacturer,
    val pharmaceuticalForm: PharmaceuticalForm,
    val series: List<Series>,
    val description: ProductDescription?,
    val imagePath: String?
) {
    constructor(
        dto: ProductCreateRequestDto,
        series: List<Series>,
        manufacturer: Manufacturer,
        pharmaceuticalForm: PharmaceuticalForm
    ) : this(
        article = dto.article,
        name = dto.name,
        price = dto.price,
        discount = dto.discount,
        manufacturer = manufacturer,
        pharmaceuticalForm = pharmaceuticalForm,
        series = series,
        description = dto.description?.let { ProductDescription(it) },
        imagePath = dto.imagePath
    )
}

data class ProductUpdateRequest(
    val article: String?,
    val name: String?,
    val price: Int?,
    val discount: Int?,
    val manufacturerId: Long?,
    val pharmaceuticalFormId: Long?,
    val seriesIds: List<Long>?,
    val description: ProductDescription?,
    val imagePath: String?
) {
    constructor(dto: ProductUpdateRequestDto) : this(
        article = dto.article,
        name = dto.name,
        price = dto.price,
        discount = dto.discount,
        manufacturerId = dto.manufacturerId,
        pharmaceuticalFormId = dto.pharmaceuticalFormId,
        seriesIds = dto.seriesIds,
        description = dto.description?.let { ProductDescription(it) },
        imagePath = dto.imagePath
    )
}
