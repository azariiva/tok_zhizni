package ru.mospolytech.tok_zhizni.repository.exposed

import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.mospolytech.tok_zhizni.entity.domain.*
import ru.mospolytech.tok_zhizni.repository.ProductsRepository
import ru.mospolytech.tok_zhizni.repository.exposed.table.*

@Repository
class ProductsRepositoryExposedImpl : ProductsRepository {
    @Transactional(readOnly = true)
    override fun find(): List<Product> =
        baseSelectQuery { selectAll() }
            .map { it.toProduct(seriesMapFunction(it)) }

    @Transactional(readOnly = true)
    override fun find(id: Long): Product? =
        baseSelectQuery { select { ProductsTable.id eq id } }
            .takeIf { !it.empty() }
            ?.first()?.let { it.toProduct(seriesMapFunction(it)) }

    @Transactional
    override fun create(createRequest: ProductCreateRequest): Product =
        ProductsTable
            .insert { body ->
                body[article] = createRequest.article
                body[name] = createRequest.name
                body[price] = createRequest.price
                createRequest.discount?.let { body[discount] = it }
                body[manufacturerId] = createRequest.manufacturer.id
                body[pharmaceuticalFormId] = createRequest.pharmaceuticalForm.id
                createRequest.description?.let { body[description] = it }
                createRequest.imagePath?.let { body[imagePath] = it }
            }
            .resultedValues!!.first().toProduct(
                createRequest.series,
                manufacturer = createRequest.manufacturer,
                pharmaceuticalForm = createRequest.pharmaceuticalForm
            ).also { product ->
                ProductSeriesTable.batchInsert(
                    createRequest.series,
                    shouldReturnGeneratedValues = false
                ) { data ->
                    this[ProductSeriesTable.productId] = product.id
                    this[ProductSeriesTable.seriesId] = data.id
                }
            }

    @Suppress("DuplicatedCode")
    @Transactional
    override fun update(id: Long, updateRequest: ProductUpdateRequest) {
        ProductsTable
            .update({ ProductsTable.id eq id }) { body ->
                updateRequest.article?.let { body[article] = it }
                updateRequest.name?.let { body[name] = it }
                updateRequest.price?.let { body[price] = it }
                updateRequest.discount?.let { body[discount] = it }
                updateRequest.manufacturerId?.let { body[manufacturerId] = it }
                updateRequest.pharmaceuticalFormId?.let { body[pharmaceuticalFormId] = it }
                updateRequest.description?.let { body[description] = it }
                updateRequest.imagePath?.let { body[imagePath] = it }
            }
        updateRequest.seriesIds?.toSet()?.let { requestIds ->
            val storedIds = ProductSeriesTable
                .select { ProductSeriesTable.productId eq id }
                .mapTo(HashSet()) { it[ProductSeriesTable.seriesId] }
            ProductSeriesTable.run {
                deleteWhere { productId eq id and (seriesId inList (storedIds - requestIds)) }
                batchInsert(requestIds - storedIds) { data ->
                    this[productId] = id
                    this[seriesId] = data
                }
            }
        }
    }

    @Transactional
    override fun delete(id: Long) {
        ProductsTable
            .deleteWhere { ProductsTable.id eq id }
    }

    private val groupingValues =
        (ProductsTable.columns + ManufacturersTable.columns + PharmaceuticalFormsTable.columns)

    private inline fun baseSelectQuery(request: FieldSet.() -> Query): Query =
        (ProductsTable leftJoin ProductSeriesTable leftJoin SeriesTable innerJoin ManufacturersTable innerJoin PharmaceuticalFormsTable)
            .slice(groupingValues + seriesIdsAggregate + seriesNamesAggregate)
            .request()
            .groupBy(*groupingValues.toTypedArray())
}

// FIXME: all stuff below could be implemented better

val concatSeparator = ";"
val seriesIdsAggregate = SeriesTable.id.castTo<String?>(TextColumnType()).groupConcat(concatSeparator)
val seriesNamesAggregate =
    SeriesTable.name.castTo<String?>(TextColumnType()).groupConcat(concatSeparator)

fun seriesMapFunction(row: ResultRow): List<Series> {
    val seriesIds = row[seriesIdsAggregate]?.split(concatSeparator)
    val seriesNames = row[seriesNamesAggregate]?.split(concatSeparator)

    return if (seriesIds != null && seriesNames != null) {
        seriesIds.zip(seriesNames)
            .map { seriesPair ->
                Series(
                    id = seriesPair.first.toLong(),
                    name = seriesPair.second
                )
            }
    } else emptyList()
}

fun ResultRow.toProduct(
    series: List<Series>,
    manufacturer: Manufacturer = Manufacturer(
        id = get(ManufacturersTable.id).value,
        name = get(ManufacturersTable.name)
    ),
    pharmaceuticalForm: PharmaceuticalForm = PharmaceuticalForm(
        id = get(PharmaceuticalFormsTable.id).value,
        name = get(PharmaceuticalFormsTable.name)
    ),
): Product =
    Product(
        id = get(ProductsTable.id).value,
        article = get(ProductsTable.article),
        name = get(ProductsTable.name),
        price = get(ProductsTable.price),
        discount = get(ProductsTable.discount),
        manufacturer = manufacturer,
        pharmaceuticalForm = pharmaceuticalForm,
        series = series,
        description = get(ProductsTable.description),
        imagePath = get(ProductsTable.imagePath)
    )