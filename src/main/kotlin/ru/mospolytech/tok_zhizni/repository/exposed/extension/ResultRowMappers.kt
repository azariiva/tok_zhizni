package ru.mospolytech.tok_zhizni.repository.exposed.extension

import org.jetbrains.exposed.sql.ResultRow
import ru.mospolytech.tok_zhizni.entity.Manufacturer
import ru.mospolytech.tok_zhizni.entity.PharmaceuticalForm
import ru.mospolytech.tok_zhizni.entity.Product
import ru.mospolytech.tok_zhizni.entity.Series
import ru.mospolytech.tok_zhizni.repository.exposed.table.ManufacturersTable
import ru.mospolytech.tok_zhizni.repository.exposed.table.PharmaceuticalFormsTable
import ru.mospolytech.tok_zhizni.repository.exposed.table.ProductsTable
import ru.mospolytech.tok_zhizni.repository.exposed.table.SeriesTable

fun ResultRow.toManufacturer(): Manufacturer =
    Manufacturer(
        id = get(ManufacturersTable.id).value,
        name = get(ManufacturersTable.name)
    )

fun ResultRow.toPharmaceuticalForm(): PharmaceuticalForm =
    PharmaceuticalForm(
        id = get(PharmaceuticalFormsTable.id).value,
        name = get(PharmaceuticalFormsTable.name)
    )

fun ResultRow.toSeries(): Series =
    Series(
        id = get(SeriesTable.id).value,
        name = get(SeriesTable.name)
    )

fun ResultRow.toProduct(
    series: List<Series>,
    manufacturer: Manufacturer = toManufacturer(),
    pharmaceuticalForm: PharmaceuticalForm = toPharmaceuticalForm(),
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