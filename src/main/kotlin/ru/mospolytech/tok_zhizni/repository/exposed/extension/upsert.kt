package ru.mospolytech.tok_zhizni.repository.exposed.extension

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import org.jetbrains.exposed.sql.transactions.TransactionManager

class BatchInsertUpdateOnDuplicate(
    table: Table,
    private val duplicateFields: List<Column<*>>?,
    private val duplicateUpdate: List<Column<*>>
) : BatchInsertStatement(table, false) {
    
    override fun prepareSQL(transaction: Transaction) =
        duplicateFields?.let { columns ->
            duplicateUpdate.takeIf { it.isNotEmpty() }?.let { updateColumns ->
                super.prepareSQL(transaction) +
                        " ON CONFLICT " +
                        columns.joinToString(prefix = "(", postfix = ")") { it.name } +
                        " DO UPDATE SET " +
                        updateColumns.joinToString { "${transaction.identity(it)} = EXCLUDED.${transaction.identity(it)}" }
            }
        } ?: super.prepareSQL(transaction)
}

class BatchInsertIgnoreOnDuplicate(
    table: Table,
    private val duplicateFields: List<Column<*>>?,
) : BatchInsertStatement(table, false) {
    
    override fun prepareSQL(transaction: Transaction) =
        duplicateFields?.let { columns ->
            super.prepareSQL(transaction) +
                    " ON CONFLICT ${columns.joinToString(prefix = "(", postfix = ")") { it.name }} DO NOTHING"
        } ?: super.prepareSQL(transaction)
}

fun <T : Table, E> T.batchInsertOnDuplicateUpdate(
    data: List<E>,
    uniqueFields: List<Column<*>>? = null,
    onDupUpdateColumns: List<Column<*>>,
    body: T.(BatchInsertUpdateOnDuplicate, E) -> Unit
) =
    data.takeIf { it.isNotEmpty() }?.let {
        with(BatchInsertUpdateOnDuplicate(this, uniqueFields, onDupUpdateColumns)) {
            it.forEach {
                this.addBatch()
                body(this, it)
            }
            TransactionManager.current().exec(this)
        }
    } ?: 0

fun <T : Table, E> T.batchInsertOnDuplicateIgnore(
    data: List<E>,
    uniqueFields: List<Column<*>>? = null,
    body: T.(BatchInsertIgnoreOnDuplicate, E) -> Unit
) =
    data.takeIf { it.isNotEmpty() }?.let {
        with(BatchInsertIgnoreOnDuplicate(this, uniqueFields)) {
            it.forEach {
                this.addBatch()
                body(this, it)
            }
            TransactionManager.current().exec(this)
        }
    } ?: 0