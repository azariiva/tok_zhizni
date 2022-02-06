package ru.mospolytech.tok_zhizni.db.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.exposed.sql.ResultRow
import ru.mospolytech.tok_zhizni.db.repository.exposed.table.PharmaceuticalFormsTable

data class PharmaceuticalForm(
    val id: Long,
    val name: String
) {
    companion object {
        fun fromResultRow(row: ResultRow): PharmaceuticalForm =
            PharmaceuticalForm(
                id = row[PharmaceuticalFormsTable.id].value,
                name = row[PharmaceuticalFormsTable.name]
            )
    }
}

data class PharmaceuticalFormCreateRequest(
    @param:JsonProperty(required = true)
    val name: String
)

data class PharmaceuticalFormUpdateRequest(
    val name: String? = null
)

