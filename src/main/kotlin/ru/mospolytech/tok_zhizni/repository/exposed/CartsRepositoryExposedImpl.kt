package ru.mospolytech.tok_zhizni.repository.exposed

import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.mospolytech.tok_zhizni.entity.domain.CartItem
import ru.mospolytech.tok_zhizni.entity.domain.CartItemUpdateRequest
import ru.mospolytech.tok_zhizni.repository.CartsRepository
import ru.mospolytech.tok_zhizni.repository.exposed.extension.batchInsertOnDuplicateUpdate
import ru.mospolytech.tok_zhizni.repository.exposed.table.*

@Repository
class CartsRepositoryExposedImpl : CartsRepository {
    @Transactional(readOnly = true)
    override fun find(userId: Long): List<CartItem> =
        baseSelectQuery {
            select { CartsTable.userId eq userId }
        }.map { it.toCartItem() }

    @Transactional
    override fun delete(userId: Long, productId: Long) {
        CartsTable.deleteWhere { CartsTable.userId eq userId and (CartsTable.productId eq productId) }
    }

    @Transactional
    override fun delete(userId: Long, productIds: List<Long>) {
        CartsTable.deleteWhere { CartsTable.userId eq userId and (CartsTable.productId inList productIds) }
    }

    @Transactional
    override fun update(userId: Long, updateRequest: CartItemUpdateRequest) {
        CartsTable.update({ CartsTable.userId eq userId and (CartsTable.productId eq updateRequest.productId) }) { body ->
            updateRequest.quantity?.let { body[quantity] = it }
        }
    }

    @Transactional
    override fun update(userId: Long, updateRequest: List<CartItemUpdateRequest>) {
        CartsTable.batchInsertOnDuplicateUpdate(updateRequest,
            listOf(CartsTable.productId, CartsTable.userId),
            listOf(CartsTable.quantity)
        ) { body, item ->
            body[productId] = item.productId
            body[this.userId] = userId
            item.quantity?.let { body[quantity] = it }
        }
    }

    private val groupingValues =
        (ProductsTable.columns + ManufacturersTable.columns + PharmaceuticalFormsTable.columns + CartsTable.columns)

    private inline fun baseSelectQuery(request: FieldSet.() -> Query): Query =
        (CartsTable fullJoin ProductsTable leftJoin ProductSeriesTable leftJoin SeriesTable innerJoin ManufacturersTable innerJoin PharmaceuticalFormsTable)
            .slice(groupingValues + seriesIdsAggregate + seriesNamesAggregate)
            .request()
            .groupBy(*groupingValues.toTypedArray())

    private fun ResultRow.toCartItem(): CartItem =
        CartItem(
            product = toProduct(seriesMapFunction(this)),
            quantity = get(CartsTable.quantity)
        )
}