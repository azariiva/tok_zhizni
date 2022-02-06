package ru.mospolytech.tok_zhizni.repository.exposed

import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.mospolytech.tok_zhizni.entity.Manufacturer
import ru.mospolytech.tok_zhizni.entity.ManufacturerCreateRequest
import ru.mospolytech.tok_zhizni.entity.ManufacturerUpdateRequest
import ru.mospolytech.tok_zhizni.repository.ManufacturersRepository
import ru.mospolytech.tok_zhizni.repository.exposed.extension.toManufacturer
import ru.mospolytech.tok_zhizni.repository.exposed.table.ManufacturersTable

@Repository
class ManufacturersRepositoryExposedImpl : ManufacturersRepository {
    @Transactional(readOnly = true)
    override fun find(): List<Manufacturer> =
        ManufacturersTable
            .selectAll()
            .map(ResultRow::toManufacturer)

    @Transactional(readOnly = true)
    override fun find(id: Long): Manufacturer? =
        ManufacturersTable
            .select { ManufacturersTable.id eq id }
            .takeIf { !it.empty() }?.first()?.toManufacturer()

    @Transactional
    override fun create(createRequest: ManufacturerCreateRequest): Manufacturer =
        ManufacturersTable
            .insert { body ->
                body[name] = createRequest.name
            }
            .resultedValues!!.first().toManufacturer()

    @Transactional
    override fun update(id: Long, updateRequest: ManufacturerUpdateRequest) {
        ManufacturersTable
            .update({ ManufacturersTable.id eq id }) { body ->
                updateRequest.name?.let { body[name] = it }
            }
    }

    @Transactional
    override fun delete(id: Long) {
        ManufacturersTable
            .deleteWhere { ManufacturersTable.id eq id }
    }
}