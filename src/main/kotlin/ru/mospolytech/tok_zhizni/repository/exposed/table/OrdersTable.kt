package ru.mospolytech.tok_zhizni.repository.exposed.table

import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.jetbrains.exposed.dao.id.IdTable
import ru.mospolytech.tok_zhizni.entity.domain.Shipment
import ru.mospolytech.tok_zhizni.repository.exposed.extension.jsonb

object OrdersTable : IdTable<Long>("orders") {
    override val id = long("id").autoIncrement("orders_id_seq").entityId()
    val userId = long("user_id").references(UsersTable.id)
    val shipment = jsonb("shipment", Shipment::class.java, jacksonObjectMapper().registerModule(JodaModule()))
    val totalPrice = integer("total_price")
    override val primaryKey: PrimaryKey by lazy { super.primaryKey ?: PrimaryKey(id) }
}