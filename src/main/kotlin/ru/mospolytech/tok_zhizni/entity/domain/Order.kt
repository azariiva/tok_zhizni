package ru.mospolytech.tok_zhizni.entity.domain

import org.joda.time.DateTime
import ru.mospolytech.tok_zhizni.entity.dto.OrderCreateRequestDto
import ru.mospolytech.tok_zhizni.entity.dto.OrderDto
import ru.mospolytech.tok_zhizni.entity.dto.OrderUpdateRequestDto
import ru.mospolytech.tok_zhizni.entity.dto.ShipmentDto


data class Shipment(
    val method: String,
    val status: Status,
    val statusChanged: DateTime,
    val address: Address,
    val phoneNumber: String,
    val price: Int
) {
    companion object {
        enum class Status {
            CREATED,
            BEING_COLLECTED,
            IN_PROGRESS,
            FINISHED
        }

        data class Address(
            val city: String,
            val street: String,
            val index: String
        ) {
            constructor(dto: ShipmentDto.Companion.AddressDto) : this(dto.city!!, dto.street!!, dto.index!!)
        }
    }

    constructor(dto: ShipmentDto) : this(
        dto.method!!,
        dto.status!!,
        dto.statusChanged!!,
        Address(dto.address!!),
        dto.phoneNumber!!,
        dto.price!!
    )
}

data class Order(
    val id: Long,
    val userId: Long,
    val shipment: Shipment,
    val items: List<Item>,
    val totalPrice: Int,
) {
    companion object {
        data class Item(
            val productId: Long,
            val quantity: Int
        ) {
            constructor(dto: OrderDto.Companion.ItemDto) : this(dto.productId!!, dto.quantity!!)
        }
    }
}

data class OrderCreateRequest(
    val userId: Long,
    val shipment: Shipment,
    val items: List<Order.Companion.Item>,
    val totalPrice: Int
) {
    constructor(userId: Long, dto: OrderCreateRequestDto) : this(
        userId,
        Shipment(dto.shipment!!),
        dto.items!!.map { Order.Companion.Item(it) },
        dto.totalPrice!!
    )
}

data class OrderUpdateRequest(
    val shipment: Shipment?,
    val items: List<Order.Companion.Item>?,
    val totalPrice: Int?,
) {
    constructor(dto: OrderUpdateRequestDto) : this(
        dto.shipment,
        dto.items?.map { Order.Companion.Item(it) },
        dto.totalPrice
    )
}