package ru.mospolytech.tok_zhizni.entity.domain

import ru.mospolytech.tok_zhizni.entity.dto.CartItemUpdateRequestDto

data class CartItem(
    val product: Product,
    val quantity: Int
)

data class CartItemUpdateRequest(
    val productId: Long,
    val quantity: Int?
) {
    constructor(dto: CartItemUpdateRequestDto) : this(dto.productId!!, dto.quantity!!)
}