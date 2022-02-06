package ru.mospolytech.tok_zhizni.db.repository.exposed.table

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.jetbrains.exposed.dao.id.IdTable
import ru.mospolytech.tok_zhizni.db.entity.ProductDescription
import ru.mospolytech.tok_zhizni.db.extension.jsonb

object ProductsTable: IdTable<Long>("products") {
    override val id = long("id").autoIncrement("products_id_seq").entityId()
    val article = varchar("article", 32)
    val name = text("name")
    val price = integer("price")
    val discount = integer("discount").default(0)
    val manufacturerId = long("manufacturer_id").references(ManufacturersTable.id)
    val pharmaceuticalFormId = long("pharmaceutical_form_id").references(PharmaceuticalFormsTable.id)
    val description = jsonb("description", ProductDescription::class.java, jacksonObjectMapper()).nullable()
    val imagePath = text("image_path").nullable()

    override val primaryKey: PrimaryKey by lazy { super.primaryKey ?: PrimaryKey(ManufacturersTable.id) }
}