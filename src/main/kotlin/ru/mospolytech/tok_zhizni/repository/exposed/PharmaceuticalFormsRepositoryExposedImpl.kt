package ru.mospolytech.tok_zhizni.repository.exposed

import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.mospolytech.tok_zhizni.entity.domain.PharmaceuticalForm
import ru.mospolytech.tok_zhizni.entity.domain.PharmaceuticalFormCreateRequest
import ru.mospolytech.tok_zhizni.entity.domain.PharmaceuticalFormUpdateRequest
import ru.mospolytech.tok_zhizni.repository.PharmaceuticalFormsRepository
import ru.mospolytech.tok_zhizni.repository.exposed.table.PharmaceuticalFormsTable

@Repository
class PharmaceuticalFormsRepositoryExposedImpl : PharmaceuticalFormsRepository {
    @Transactional(readOnly = true)
    override fun find(): List<PharmaceuticalForm> =
        PharmaceuticalFormsTable
            .selectAll()
            .map { it.toPharmaceuticalForm() }

    @Transactional(readOnly = true)
    override fun find(id: Long): PharmaceuticalForm? =
        PharmaceuticalFormsTable
            .select { PharmaceuticalFormsTable.id eq id }
            .takeIf { !it.empty() }?.first()?.toPharmaceuticalForm()

    @Transactional
    override fun create(createRequest: PharmaceuticalFormCreateRequest): PharmaceuticalForm =
        PharmaceuticalFormsTable
            .insert { body ->
                body[name] = createRequest.name
            }
            .resultedValues!!.first().toPharmaceuticalForm()

    @Transactional
    override fun update(id: Long, updateRequest: PharmaceuticalFormUpdateRequest) {
        PharmaceuticalFormsTable
            .update({ PharmaceuticalFormsTable.id eq id}) { body ->
                updateRequest.name?.let { body[name] = it }
            }
    }

    @Transactional
    override fun delete(id: Long) {
        PharmaceuticalFormsTable
            .deleteWhere { PharmaceuticalFormsTable.id eq id }
    }

    fun ResultRow.toPharmaceuticalForm(): PharmaceuticalForm =
        PharmaceuticalForm(
            id = get(PharmaceuticalFormsTable.id).value,
            name = get(PharmaceuticalFormsTable.name)
        )
}