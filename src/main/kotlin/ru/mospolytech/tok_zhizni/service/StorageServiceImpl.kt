package ru.mospolytech.tok_zhizni.service

import org.springframework.stereotype.Service
import ru.mospolytech.tok_zhizni.entity.domain.*
import ru.mospolytech.tok_zhizni.entity.dto.*
import ru.mospolytech.tok_zhizni.entity.exception.*
import ru.mospolytech.tok_zhizni.repository.*
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import javax.annotation.PostConstruct
import kotlin.io.path.deleteExisting
import kotlin.io.path.exists

@Suppress("SpellCheckingInspection")
@Service
class StorageServiceImpl(
    private val manufacturersRepository: ManufacturersRepository,
    private val pharmaceuticalFormsRepository: PharmaceuticalFormsRepository,
    private val seriesRepository: SeriesRepository,
    private val productsRepository: ProductsRepository,
    private val usersRepository: UsersRepository,
    private val cartsRepository: CartsRepository,
    private val ordersRepository: OrdersRepository
) : StorageService {
    override fun addManufacturer(createRequest: ManufacturerCreateRequestDto): ManufacturerDto =
        manufacturersRepository.create(ManufacturerCreateRequest(createRequest)).toDto()

    override fun updateManufacturer(id: Long, updateRequest: ManufacturerUpdateRequestDto) {
        manufacturersRepository.find(id) ?: throw ManufacturerNotFound()
        manufacturersRepository.update(id, ManufacturerUpdateRequest(updateRequest))
    }

    override fun getAllManufacturers(): List<ManufacturerDto> =
        manufacturersRepository.find().map { it.toDto() }

    override fun deleteManufacturer(id: Long) {
        manufacturersRepository.find(id) ?: throw ManufacturerNotFound()
        manufacturersRepository.delete(id)
    }

    override fun addPharmaceuticalForm(createRequest: PharmaceuticalFormCreateRequestDto): PharmaceuticalFormDto =
        pharmaceuticalFormsRepository.create(PharmaceuticalFormCreateRequest(createRequest)).toDto()

    override fun updatePharmaceuticalForm(id: Long, updateRequest: PharmaceuticalFormUpdateRequestDto) {
        pharmaceuticalFormsRepository.find(id) ?: throw PharmaceuticalFormNotFound()
        pharmaceuticalFormsRepository.update(id, PharmaceuticalFormUpdateRequest(updateRequest))
    }

    override fun getAllPharmaceuticalForms(): List<PharmaceuticalFormDto> =
        pharmaceuticalFormsRepository.find().map { it.toDto() }

    override fun deletePharmaceuticalForm(id: Long) {
        pharmaceuticalFormsRepository.find(id) ?: throw PharmaceuticalFormNotFound()
        pharmaceuticalFormsRepository.delete(id)
    }

    override fun addSeries(createRequest: SeriesCreateRequestDto): SeriesDto =
        seriesRepository.create(SeriesCreateRequest(createRequest)).toDto()

    override fun updateSeries(id: Long, updateRequest: SeriesUpdateRequestDto) {
        seriesRepository.find(id) ?: throw SeriesNotFound()
        seriesRepository.update(id, SeriesUpdateRequest(updateRequest))
    }

    override fun getAllSeries(): List<SeriesDto> =
        seriesRepository.find().map { it.toDto() }

    override fun deleteSeries(id: Long) {
        seriesRepository.find(id)
        seriesRepository.delete(id)
    }

    override fun addProduct(createRequest: ProductCreateRequestDto): ProductDto {
        val manufacturer = manufacturersRepository.find(createRequest.manufacturerId) ?: throw ManufacturerNotFound()
        val pharmaceuticalForm =
            pharmaceuticalFormsRepository.find(createRequest.pharmaceuticalFormId) ?: throw PharmaceuticalFormNotFound()
        val series = seriesRepository.find(createRequest.seriesIds)
        val illegalSeries = createRequest.seriesIds - series.map { it.id }

        if (illegalSeries.isNotEmpty()) throw SeriesNotFound()

        return productsRepository.create(ProductCreateRequest(createRequest, series, manufacturer, pharmaceuticalForm))
            .toDto()
    }

    override fun updateProduct(id: Long, updateRequest: ProductUpdateRequestDto) {
        productsRepository.find(id) ?: throw ProductNotFound()
        updateRequest.manufacturerId?.let { manufacturersRepository.find(it) ?: throw ManufacturerNotFound() }
        updateRequest.pharmaceuticalFormId?.let {
            pharmaceuticalFormsRepository.find(it) ?: throw PharmaceuticalFormNotFound()
        }
        updateRequest.seriesIds?.let {
            val illegalSeries = it - seriesRepository.find(it).map { ser -> ser.id }
            if (illegalSeries.isNotEmpty()) throw SeriesNotFound()
        }

        productsRepository.update(id, ProductUpdateRequest(updateRequest))
    }

    override fun getProduct(id: Long): ProductDto =
        (productsRepository.find(id) ?: throw ProductNotFound()).toDto()

    override fun getAllProducts(): List<ProductDto> =
        productsRepository.find().map { it.toDto() }

    override fun deleteProduct(id: Long) {
        productsRepository.find(id) ?: throw ProductNotFound()
        productsRepository.delete(id)
    }

    override fun addUser(createRequest: UserCreateRequestDto): UserDto {
        val user = UserCreateRequest(createRequest)
        usersRepository.findByName(user.name)?.let { throw DuplicateUserLogin() }
        usersRepository.findByEmail(user.email)?.let { throw DuplicateUserEmail() }
        return usersRepository.create(user).toDto()
    }

    override fun updateUser(id: Long, updateRequestDto: UserUpdateRequestDto) {
        val user = UserUpdateRequest(updateRequestDto)
        usersRepository.find(id) ?: throw UserNotFound()
        user.name?.let { usersRepository.findByName(it)?.let { throw DuplicateUserLogin() } }
        user.email?.let { usersRepository.findByEmail(it)?.let { throw DuplicateUserEmail() } }
        usersRepository.update(id, user)
    }

    override fun getUser(id: Long): UserDto =
        (usersRepository.find(id) ?: throw UserNotFound()).toDto()

    override fun getAllUsers(): List<UserDto> =
        usersRepository.find().map { it.toDto() }

    override fun deleteUser(id: Long) {
        usersRepository.find(id) ?: throw UserNotFound()
        usersRepository.delete(id)
    }

    override fun addImage(fileName: String, inputStream: InputStream) {
        val targetLocation = baseImagesPath.resolve(fileName)
        if (targetLocation.exists()) {
            throw DuplicateImageName()
        }
        Files.copy(inputStream, targetLocation)
    }

    override fun updateImage(fileName: String, inputStream: InputStream) {
        val targetLocation = baseImagesPath.resolve(fileName)
        if (!targetLocation.exists()) {
            throw ImageNotFound()
        }
        Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
    }

    override fun getImage(fileName: String): ByteArray {
        val targetLocation = baseImagesPath.resolve(fileName)
        if (!targetLocation.exists()) {
            throw ImageNotFound()
        }
        return File(targetLocation.toUri()).readBytes()
    }

    override fun deleteImage(fileName: String) {
        val targetLocation = baseImagesPath.resolve(fileName)
        if (!targetLocation.exists()) {
            throw ImageNotFound()
        }
        targetLocation.deleteExisting()
    }

    override fun getUserCart(userId: Long): List<CartItemDto> =
        cartsRepository.find(userId).map { it.toDto() }

    override fun updateUserCart(userId: Long, updateRequest: List<CartItemUpdateRequestDto>) {
        cartsRepository.update(
            userId,
            updateRequest.filter { it.quantity!! > 0 }.map { CartItemUpdateRequest(it) })
        cartsRepository.delete(
            userId,
            updateRequest.filter { it.quantity!! <= 0 }.map { it.productId!! })
    }

    override fun addOrder(userId: Long, createRequest: OrderCreateRequestDto): OrderDto =
        ordersRepository.create(OrderCreateRequest(userId, createRequest)).toDto()

    override fun updateOrder(orderId: Long, updateRequest: OrderUpdateRequestDto) {
        ordersRepository.update(orderId, OrderUpdateRequest(updateRequest))
    }

    override fun getOrder(id: Long): OrderDto =
        ordersRepository.find(id)?.toDto() ?: throw OrderNotFound()

    override fun getAllOrders(): List<OrderDto> =
        ordersRepository.find().map { it.toDto() }

    override fun getAllOrders(userId: Long): List<OrderDto> =
        ordersRepository.findByUser(userId).map { it.toDto() }

    companion object {
        private val baseImagesPath = Paths.get("src/main/resources/static/images/").toAbsolutePath().normalize()
    }

    @PostConstruct
    fun createImagesFolder() {
        File(baseImagesPath.toUri()).let { imagesDir ->
            if (!imagesDir.exists()) {
                imagesDir.mkdirs()
            }
        }
    }
}