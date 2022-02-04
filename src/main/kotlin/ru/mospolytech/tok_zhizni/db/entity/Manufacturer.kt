package ru.mospolytech.tok_zhizni.db.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.exposed.sql.ResultRow
import ru.mospolytech.tok_zhizni.db.repository.exposed.table.ManufacturersTable

data class Manufacturer(
    val id: Long,
    val name: String
) {
    companion object {
        fun fromResultRow(row: ResultRow): Manufacturer =
            Manufacturer(
                id = row[ManufacturersTable.id].value,
                name = row[ManufacturersTable.name]
            )
    }
}

data class ManufacturerCreateRequest(
    @param:JsonProperty(required = true)
    val name: String
)

data class ManufacturerUpdateRequest(
    val name: String? = null
)