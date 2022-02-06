package ru.mospolytech.tok_zhizni.db.repository.exposed.table

import org.jetbrains.exposed.sql.Table

object ProductSeriesTable: Table("product_series") {
    val productId = long("product_id").references(ProductsTable.id)
    val seriesId = long("series_id").references(SeriesTable.id)
}