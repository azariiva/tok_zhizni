package ru.mospolytech.tok_zhizni.db.repository.exposed

import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.mospolytech.tok_zhizni.db.entity.Series
import ru.mospolytech.tok_zhizni.db.entity.SeriesCreateRequest
import ru.mospolytech.tok_zhizni.db.entity.SeriesUpdateRequest
import ru.mospolytech.tok_zhizni.db.repository.SeriesRepository
import ru.mospolytech.tok_zhizni.db.repository.exposed.table.SeriesTable

@Repository
class SeriesRepositoryExposedImpl : SeriesRepository {
    @Transactional(readOnly = true)
    override fun find(): List<Series> =
        SeriesTable
            .selectAll()
            .map(Series::fromResultRow)

    @Transactional(readOnly = true)
    override fun find(id: Long): Series? =
        SeriesTable
            .select { SeriesTable.id eq id }
            .takeIf { !it.empty() }
            ?.let { Series.fromResultRow(it.first()) }

    @Transactional
    override fun create(createRequest: SeriesCreateRequest): Series =
        SeriesTable
            .insert { body ->
                body[name] = createRequest.name
            }
            .resultedValues!!
            .let { Series.fromResultRow(it.first()) }

    @Transactional
    override fun update(id: Long, updateRequest: SeriesUpdateRequest) {
        SeriesTable
            .update({ SeriesTable.id eq id }) { body ->
                updateRequest.name?.let { body[name] = it }
            }
    }

    @Transactional
    override fun delete(id: Long) {
        SeriesTable
            .deleteWhere { SeriesTable.id eq id }
    }
}