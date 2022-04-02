package ru.mospolytech.tok_zhizni.entity.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.time.DateTime
import ru.mospolytech.tok_zhizni.entity.domain.Order
import ru.mospolytech.tok_zhizni.entity.domain.Shipment
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

data class ShipmentDto(
    @param:[NotEmpty JsonProperty(required = true)]
    val method: String?,

    @param:[NotEmpty JsonProperty(required = true)]
    val status: Shipment.Companion.Status?,

    @param:[NotEmpty JsonProperty(required = true)]
    val statusChanged: DateTime?,

    @param:[NotEmpty JsonProperty(required = true)]
    val address: AddressDto?,

    @param:[NotEmpty Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})\$") JsonProperty(required = true)]
    val phoneNumber: String?,

    @param:[NotEmpty JsonProperty(required = true)]
    val price: Int?
) {
    companion object {
        data class AddressDto(
            @param:[NotEmpty JsonProperty(required = true)]
            val city: String?,

            @param:[NotEmpty JsonProperty(required = true)]
            val street: String?,

            @param:[NotEmpty Pattern(regexp = "^\\d{6}\$") JsonProperty(required = true)]
            val index: String?
        )
    }
}

data class OrderDto(
    @param:[NotEmpty JsonProperty(required = true)]
    val id: Long?,

    @param:[NotEmpty JsonProperty(required = true)]
    val userId: Long?,

    @param:[NotEmpty JsonProperty(required = true)]
    val shipment: ShipmentDto?,

    @param:[NotEmpty JsonProperty(required = true)]
    val items: List<ItemDto>?,

    @param:[NotEmpty JsonProperty(required = true)]
    val totalPrice: Int?,
) {
    companion object {
        data class ItemDto(
            @param:[NotEmpty JsonProperty(required = true)]
            val productId: Long?,

            @param:[NotEmpty JsonProperty(required = true)]
            val quantity: Int?
        )
    }
}

data class OrderCreateRequestDto(
    @param:[NotEmpty JsonProperty(required = true)]
    val shipment: ShipmentDto?,

    @param:[NotEmpty JsonProperty(required = true)]
    val items: List<OrderDto.Companion.ItemDto>?,

    @param:[NotEmpty JsonProperty(required = true)]
    val totalPrice: Int?
)


data class OrderUpdateRequestDto(
    val shipment: Shipment?,
    val items: List<OrderDto.Companion.ItemDto>?,
    val totalPrice: Int?,
)

fun Shipment.Companion.Address.toDto(): ShipmentDto.Companion.AddressDto =
    ShipmentDto.Companion.AddressDto(
        city = city, street = street, index = index
    )

fun Shipment.toDto(): ShipmentDto =
    ShipmentDto(
        method = method,
        status = status,
        statusChanged = statusChanged,
        address = address.toDto(),
        phoneNumber = phoneNumber,
        price = price
    )

fun Order.Companion.Item.toDto(): OrderDto.Companion.ItemDto =
    OrderDto.Companion.ItemDto(
        productId = productId, quantity = quantity
    )

fun Order.toDto(): OrderDto =
    OrderDto(
        id = id, shipment = shipment.toDto(), items = items.map { it.toDto() }, totalPrice = totalPrice, userId = userId
    )