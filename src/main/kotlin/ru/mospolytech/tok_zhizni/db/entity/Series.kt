package ru.mospolytech.tok_zhizni.db.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.exposed.sql.ResultRow
import ru.mospolytech.tok_zhizni.db.repository.exposed.table.SeriesTable

data class Series(
    val id: Long,
    val name: String
) {
    companion object {
        fun fromResultRow(row: ResultRow): Series =
            Series(
                id = row[SeriesTable.id].value,
                name = row[SeriesTable.name]
            )
    }
}

data class SeriesCreateRequest(
    @param:JsonProperty(required=true)
    val name: String
)

data class SeriesUpdateRequest(
    val name: String? = null
)
