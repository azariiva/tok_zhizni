package ru.mospolytech.tok_zhizni.db.entity

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.exposed.sql.ResultRow
import ru.mospolytech.tok_zhizni.db.repository.exposed.table.ProductsTable

data class ProductDescription(
    @param:[JsonProperty("Состав продукта") JsonAlias("Состав")]
    val composition: String? = null,
    @param:[JsonProperty("Стандарт упаковки") JsonAlias("Упаковка")]
    val packaging: String? = null,
    @param:JsonProperty("Срок годности")
    val shelfLife: String? = null,
    @param:JsonProperty("Способ хранения")
    val storageMethod: String? = null,
    @param:JsonProperty("Способ применения")
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
) {
    companion object {
       inline fun fromResultRow(row: ResultRow, crossinline seriesMapFunction: (row: ResultRow) -> List<Series>): Product =
           Product(
               id = row[ProductsTable.id].value,
               article = row[ProductsTable.article],
               name = row[ProductsTable.name],
               price = row[ProductsTable.price],
               discount = row[ProductsTable.discount],
               manufacturer = Manufacturer.fromResultRow(row),
               pharmaceuticalForm = PharmaceuticalForm.fromResultRow(row),
               series = seriesMapFunction(row),
               description = row[ProductsTable.description],
               imagePath = row[ProductsTable.imagePath]
           )
    }
}

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
