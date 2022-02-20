package ru.mospolytech.tok_zhizni.service

import ru.mospolytech.tok_zhizni.entity.dto.*
import java.io.InputStream


@Suppress("SpellCheckingInspection")
interface StorageService {
    fun addManufacturer(createRequest: ManufacturerCreateRequestDto): ManufacturerDto
    fun updateManufacturer(id: Long, updateRequest: ManufacturerUpdateRequestDto)
    fun getAllManufacturers(): List<ManufacturerDto>
    fun deleteManufacturer(id: Long)

    fun addPharmaceuticalForm(createRequest: PharmaceuticalFormCreateRequestDto): PharmaceuticalFormDto
    fun updatePharmaceuticalForm(id: Long, updateRequest: PharmaceuticalFormUpdateRequestDto)
    fun getAllPharmaceuticalForms(): List<PharmaceuticalFormDto>
    fun deletePharmaceuticalForm(id: Long)

    fun addSeries(createRequest: SeriesCreateRequestDto): SeriesDto
    fun updateSeries(id: Long, updateRequest: SeriesUpdateRequestDto)
    fun getAllSeries(): List<SeriesDto>
    fun deleteSeries(id: Long)

    fun addProduct(createRequest: ProductCreateRequestDto): ProductDto
    fun updateProduct(id: Long, updateRequest: ProductUpdateRequestDto)
    fun getProduct(id: Long): ProductDto
    fun getAllProducts(): List<ProductDto>
    fun deleteProduct(id: Long)

    fun addUser(createRequest: UserCreateRequestDto): UserDto
    fun updateUser(id: Long, updateRequestDto: UserUpdateRequestDto)
    fun getUser(id: Long): UserDto
    fun getAllUsers(): List<UserDto>
    fun deleteUser(id: Long)

    fun addImage(fileName: String, inputStream: InputStream)
    fun updateImage(fileName: String, inputStream: InputStream)
    fun getImage(fileName: String): ByteArray
    fun deleteImage(fileName: String)

    fun getUserCart(userId: Long): List<CartItemDto>
    fun updateUserCart(userId: Long, updateRequest: List<CartItemUpdateRequestDto>)
}