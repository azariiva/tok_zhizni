package ru.mospolytech.tok_zhizni.repository.exposed.table

import org.jetbrains.exposed.dao.id.IdTable

object ManufacturersTable: IdTable<Long>("manufacturers") {
    override val id = long("id").autoIncrement("manufacturers_id_seq").entityId()
    val name = text("name")

    override val primaryKey: PrimaryKey by lazy { super.primaryKey ?: PrimaryKey(id) }
}