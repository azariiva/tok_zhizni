package ru.mospolytech.tok_zhizni.db.repository.exposed

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.mospolytech.tok_zhizni.db.entity.Manufacturer
import ru.mospolytech.tok_zhizni.db.entity.ManufacturerCreateRequest
import ru.mospolytech.tok_zhizni.db.entity.ManufacturerUpdateRequest
import ru.mospolytech.tok_zhizni.db.repository.ManufacturersRepository
import ru.mospolytech.tok_zhizni.db.repository.exposed.table.ManufacturersTable

@Repository
class ManufacturersRepositoryExposedImpl : ManufacturersRepository {
    @Transactional(readOnly = true)
    override fun find(): List<Manufacturer> =
        ManufacturersTable
            .selectAll()
            .map(Manufacturer::fromResultRow)

    @Transactional(readOnly = true)
    override fun find(id: Long): Manufacturer? =
        ManufacturersTable
            .select { ManufacturersTable.id eq id }
            .takeIf { !it.empty() }
            ?.let { Manufacturer.fromResultRow(it.first()) }

    @Transactional
    override fun create(createRequest: ManufacturerCreateRequest): Manufacturer =
        ManufacturersTable
            .insert { body ->
                body[name] = createRequest.name
            }
            .resultedValues!!
            .let { Manufacturer.fromResultRow(it.first()) }

    @Transactional
    override fun update(id: Long, updateRequest: ManufacturerUpdateRequest) {
        ManufacturersTable
            .update({ ManufacturersTable.id eq id }) { body ->
                updateRequest.name?.let { body[name] = name }
            }
    }
}