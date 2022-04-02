package ru.mospolytech.tok_zhizni.repository

import ru.mospolytech.tok_zhizni.entity.domain.Order
import ru.mospolytech.tok_zhizni.entity.domain.OrderCreateRequest
import ru.mospolytech.tok_zhizni.entity.domain.OrderUpdateRequest

interface OrdersRepository: CrudRepository<OrderCreateRequest, OrderUpdateRequest, Order> {
    fun findByUser(userId: Long): List<Order>
}