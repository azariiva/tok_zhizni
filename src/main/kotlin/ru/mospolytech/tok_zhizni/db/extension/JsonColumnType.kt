package ru.mospolytech.tok_zhizni.db.extension

import com.fasterxml.jackson.databind.ObjectMapper
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.IColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.api.PreparedStatementApi
import org.postgresql.util.PGobject
import ru.mospolytech.tok_zhizni.config.LOGGER

fun <T : Any> Table.jsonb(name: String, klass: Class<T>, mapper: ObjectMapper): Column<T> =
    registerColumn(name, JsonColumnType(klass, mapper))

class JsonColumnType<out T : Any>(
    private val klass: Class<T>,
    private val mapper: ObjectMapper,
    override var nullable: Boolean = false
) : IColumnType {

    override fun sqlType() = "jsonb"

    override fun setParameter(stmt: PreparedStatementApi, index: Int, value: Any?) {
        val obj = PGobject()
        obj.type = "jsonb"
        if (value != null)
            obj.value = value as String
        stmt[index] = obj
    }

    override fun valueFromDB(value: Any) =
        try {
            if (value::class.java == klass) {
                value
            } else {
                val json = (value as PGobject).value
                mapper.readValue(json, klass)
            }
        } catch (e: Exception) {
            LOGGER.error("Can't parse JSON: ${(value as PGobject)}")
        }

    override fun notNullValueToDB(value: Any): Any = mapper.writeValueAsString(value)
    override fun nonNullValueToString(value: Any): String = "'${mapper.writeValueAsString(value)}'"
}
