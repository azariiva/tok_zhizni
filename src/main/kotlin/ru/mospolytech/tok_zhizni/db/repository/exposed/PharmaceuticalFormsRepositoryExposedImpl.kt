package ru.mospolytech.tok_zhizni.db.repository.exposed

import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.mospolytech.tok_zhizni.db.entity.PharmaceuticalForm
import ru.mospolytech.tok_zhizni.db.entity.PharmaceuticalFormCreateRequest
import ru.mospolytech.tok_zhizni.db.entity.PharmaceuticalFormUpdateRequest
import ru.mospolytech.tok_zhizni.db.repository.PharmaceuticalFormsRepository
import ru.mospolytech.tok_zhizni.db.repository.exposed.table.PharmaceuticalFormsTable

@Repository
class PharmaceuticalFormsRepositoryExposedImpl : PharmaceuticalFormsRepository {
    @Transactional(readOnly = true)
    override fun find(): List<PharmaceuticalForm> =
        PharmaceuticalFormsTable
            .selectAll()
            .map(PharmaceuticalForm::fromResultRow)

    @Transactional(readOnly = true)
    override fun find(id: Long): PharmaceuticalForm? =
        PharmaceuticalFormsTable
            .select { PharmaceuticalFormsTable.id eq id }
            .takeIf { !it.empty() }
            ?.let { PharmaceuticalForm.fromResultRow(it.first()) }

    @Transactional
    override fun create(createRequest: PharmaceuticalFormCreateRequest): PharmaceuticalForm =
        PharmaceuticalFormsTable
            .insert { body ->
                body[name] = createRequest.name
            }
            .resultedValues!!
            .let { PharmaceuticalForm.fromResultRow(it.first()) }

    @Transactional
    override fun update(id: Long, updateRequest: PharmaceuticalFormUpdateRequest) {
        PharmaceuticalFormsTable
            .update({PharmaceuticalFormsTable.id eq id}) { body ->
                updateRequest.name?.let { body[name] = it }
            }
    }

    @Transactional
    override fun delete(id: Long) {
        PharmaceuticalFormsTable
            .deleteWhere { PharmaceuticalFormsTable.id eq id }
    }
}