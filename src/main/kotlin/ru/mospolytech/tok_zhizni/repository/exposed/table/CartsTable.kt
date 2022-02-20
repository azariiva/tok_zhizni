package ru.mospolytech.tok_zhizni.repository.exposed.table

import org.jetbrains.exposed.sql.Table

object CartsTable: Table("carts") {
    val productId = long("product_id").references(ProductsTable.id)
    val userId = long("user_id").references(UsersTable.id)
    val quantity = integer("quantity").default(1)
}