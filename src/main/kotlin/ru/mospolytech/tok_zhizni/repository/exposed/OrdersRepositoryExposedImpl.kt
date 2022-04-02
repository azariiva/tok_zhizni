package ru.mospolytech.tok_zhizni.repository.exposed

import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.mospolytech.tok_zhizni.entity.domain.Order
import ru.mospolytech.tok_zhizni.entity.domain.OrderCreateRequest
import ru.mospolytech.tok_zhizni.entity.domain.OrderUpdateRequest
import ru.mospolytech.tok_zhizni.repository.OrdersRepository
import ru.mospolytech.tok_zhizni.repository.exposed.extension.batchInsertOnDuplicateUpdate
import ru.mospolytech.tok_zhizni.repository.exposed.table.OrderItemsTable
import ru.mospolytech.tok_zhizni.repository.exposed.table.OrdersTable

@Repository
class OrdersRepositoryExposedImpl : OrdersRepository {
    @Transactional(readOnly = true)
    override fun findByUser(userId: Long): List<Order> =
        baseSelectQuery { select { OrdersTable.userId eq userId } }
            .map { it.toOrder(itemMapFunction(it)) }

    @Transactional(readOnly = true)
    override fun find(): List<Order> =
        baseSelectQuery { selectAll() }.map { it.toOrder(itemMapFunction(it)) }

    @Transactional(readOnly = true)
    override fun find(id: Long): Order? =
        baseSelectQuery { select { OrdersTable.id eq id } }
            .takeIf { !it.empty() }
            ?.first()?.let { it.toOrder(itemMapFunction(it)) }

    @Transactional
    override fun create(createRequest: OrderCreateRequest): Order =
        OrdersTable
            .insert { body ->
                body[userId] = createRequest.userId
                body[shipment] = createRequest.shipment
                body[totalPrice] = createRequest.totalPrice
            }
            .resultedValues!!.first().toOrder(createRequest.items)
            .also { order ->
                OrderItemsTable.batchInsert(
                    createRequest.items,
                    shouldReturnGeneratedValues = false
                ) { data ->
                    this[OrderItemsTable.productId] = data.productId
                    this[OrderItemsTable.quantity] = data.quantity
                    this[OrderItemsTable.orderId] = order.id
                }
            }

    @Transactional
    override fun update(id: Long, updateRequest: OrderUpdateRequest) {
        OrdersTable
            .update({ OrdersTable.id eq id }) { body ->
                updateRequest.totalPrice?.let { body[totalPrice] = it }
                updateRequest.shipment?.let { body[shipment] = it }
            }
        updateRequest.items?.toSet()?.let { items ->
            val storedItems = OrderItemsTable
                .select { OrderItemsTable.orderId eq id }
                .mapTo(HashSet()) { it.toItem() }
            OrderItemsTable.run {
                deleteWhere { orderId eq id and (productId inList (storedItems - items).map { it.productId }) }
                batchInsertOnDuplicateUpdate(
                    data = storedItems.intersect(items).toList(),
                    uniqueFields = listOf(orderId, productId),
                    onDupUpdateColumns = listOf(quantity),
                ) { body, item ->
                    body[productId] = item.productId
                    body[orderId] = id
                    body[quantity] = item.quantity
                }
                batchInsert(
                    items - storedItems,
                    shouldReturnGeneratedValues = false
                ) { data ->
                    this[productId] = data.productId
                    this[quantity] = data.quantity
                    this[orderId] = id
                }
            }
        }
    }

    @Transactional
    override fun delete(id: Long) {
        OrdersTable
            .deleteWhere { OrdersTable.id eq id }
    }

    private val groupingValues = OrdersTable.columns
    private val itemIdsAggregate =
        OrderItemsTable.productId.castTo<String?>(TextColumnType()).groupConcat(concatSeparator)
    private val itemQuantitiesAggregate =
        OrderItemsTable.quantity.castTo<String?>(TextColumnType()).groupConcat(concatSeparator)

    private inline fun baseSelectQuery(request: FieldSet.() -> Query): Query =
        (OrdersTable innerJoin OrderItemsTable)
            .slice(groupingValues + itemIdsAggregate + itemQuantitiesAggregate)
            .request()
            .groupBy(*groupingValues.toTypedArray())

    private fun ResultRow.toOrder(
        items: List<Order.Companion.Item>
    ): Order =
        Order(
            id = get(OrdersTable.id).value,
            userId = get(OrdersTable.userId),
            shipment = get(OrdersTable.shipment),
            items = items,
            totalPrice = get(OrdersTable.totalPrice)
        )

    private fun ResultRow.toItem(): Order.Companion.Item =
        Order.Companion.Item(
            productId = get(OrderItemsTable.productId),
            quantity = get(OrderItemsTable.quantity)
        )

    fun itemMapFunction(
        row: ResultRow
    ): List<Order.Companion.Item> {
        val itemIds = row[itemIdsAggregate]?.split(concatSeparator)
        val itemQuantities = row[itemQuantitiesAggregate]?.split(concatSeparator)

        return if (itemIds != null && itemQuantities != null) {
            itemIds.zip(itemQuantities)
                .map { seriesPair ->
                    Order.Companion.Item(
                        productId = seriesPair.first.toLong(),
                        quantity = seriesPair.second.toInt()
                    )
                }
        } else emptyList()
    }
}