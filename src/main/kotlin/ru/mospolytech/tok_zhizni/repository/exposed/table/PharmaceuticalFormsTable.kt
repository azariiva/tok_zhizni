package ru.mospolytech.tok_zhizni.repository.exposed.table

import org.jetbrains.exposed.dao.id.IdTable

object PharmaceuticalFormsTable: IdTable<Long>("pharmaceutical_forms") {
    override val id = long("id").autoIncrement("pharmaceutical_forms_id_seq").entityId()
    val name = text("name")

    override val primaryKey: PrimaryKey by lazy { super.primaryKey ?: PrimaryKey(ManufacturersTable.id) }
}