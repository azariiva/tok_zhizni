package ru.mospolytech.tok_zhizni.repository.exposed.table

import org.jetbrains.exposed.sql.Table

object OrderItemsTable: Table("order_items") {
    val orderId = long("order_id").references(OrdersTable.id)
    val productId = long("product_id").references(ProductsTable.id)
    val quantity = integer("quantity")
}