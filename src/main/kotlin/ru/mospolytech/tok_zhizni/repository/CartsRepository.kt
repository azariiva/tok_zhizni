package ru.mospolytech.tok_zhizni.repository

import ru.mospolytech.tok_zhizni.entity.domain.CartItem
import ru.mospolytech.tok_zhizni.entity.domain.CartItemUpdateRequest

interface CartsRepository {
    fun find(userId: Long): List<CartItem>
    fun delete(userId: Long, productId: Long)
    fun delete(userId: Long, productIds: List<Long>)
    fun update(userId: Long, updateRequest: CartItemUpdateRequest)
    fun update(userId: Long, updateRequest: List<CartItemUpdateRequest>)
}