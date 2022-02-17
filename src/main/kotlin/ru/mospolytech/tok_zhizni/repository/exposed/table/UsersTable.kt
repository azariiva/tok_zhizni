package ru.mospolytech.tok_zhizni.repository.exposed.table

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.jodatime.date
import org.joda.time.DateTime

object UsersTable: IdTable<Long>("users") {
    override val id = long("id").autoIncrement("users_id_seq").entityId()
    val name = varchar("name", 255).uniqueIndex()
    val password = binary("password")
    val email = varchar("email", 255)
    val isVerified = bool("is_verified").default(false)
    val registered = date("registered_at_ts").clientDefault { DateTime.now() }

    override val primaryKey: PrimaryKey by lazy { super.primaryKey ?: PrimaryKey(ManufacturersTable.id) }
}