package ru.mospolytech.tok_zhizni.repository.exposed

import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.mospolytech.tok_zhizni.entity.Series
import ru.mospolytech.tok_zhizni.entity.SeriesCreateRequest
import ru.mospolytech.tok_zhizni.entity.SeriesUpdateRequest
import ru.mospolytech.tok_zhizni.repository.SeriesRepository
import ru.mospolytech.tok_zhizni.repository.exposed.extension.toSeries
import ru.mospolytech.tok_zhizni.repository.exposed.table.SeriesTable

@Repository
class SeriesRepositoryExposedImpl : SeriesRepository {
    @Transactional(readOnly = true)
    override fun find(): List<Series> =
        SeriesTable
            .selectAll()
            .map(ResultRow::toSeries)

    @Transactional(readOnly = true)
    override fun find(ids: List<Long>): List<Series> =
        SeriesTable
            .select { SeriesTable.id inList ids }
            .map(ResultRow::toSeries)

    @Transactional(readOnly = true)
    override fun find(id: Long): Series? =
        SeriesTable
            .select { SeriesTable.id eq id }
            .takeIf { !it.empty() }?.first()?.toSeries()

    @Transactional
    override fun create(createRequest: SeriesCreateRequest): Series =
        SeriesTable
            .insert { body ->
                body[name] = createRequest.name
            }
            .resultedValues!!.first().toSeries()

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