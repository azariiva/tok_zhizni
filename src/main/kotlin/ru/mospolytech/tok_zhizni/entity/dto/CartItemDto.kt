package ru.mospolytech.tok_zhizni.entity.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ru.mospolytech.tok_zhizni.entity.domain.CartItem
import ru.mospolytech.tok_zhizni.entity.domain.Product
import javax.validation.constraints.NotEmpty

data class CartItemDto(
    val product: Product,
    val quantity: Int
)

data class CartItemUpdateRequestDto(
    @param:[NotEmpty JsonProperty(required = true)]
    val productId: Long?,
    @param:[NotEmpty JsonProperty(required = true)]
    val quantity: Int?
)

fun CartItem.toDto(): CartItemDto =
    CartItemDto(
        product = product,
        quantity = quantity
    )