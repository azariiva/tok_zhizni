package ru.mospolytech.tok_zhizni.rest.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.mospolytech.tok_zhizni.entity.domain.User
import ru.mospolytech.tok_zhizni.entity.dto.*
import ru.mospolytech.tok_zhizni.entity.exception.PermissionDenied
import ru.mospolytech.tok_zhizni.service.StorageService
import javax.annotation.security.RolesAllowed
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import javax.validation.Valid


@Api(value = "", tags = ["Storage Controller"])
@CrossOrigin(
    allowCredentials = "true",
    origins = [
        "http://localhost:3000",
        "http://localhost:3030",
//        "https://tokzhizni.com",
//        "https://www.tokzhizni.com",
//        "https://www.tokzhizni.com:443",
//        "https://tokzhizni.com:443",
//        "https://tokzhizni.com/admin",
//        "https://www.tokzhizni.com/admin",
//        "https://tokzhizni.com:443/admin",
//        "https://www.tokzhizni.com:443/admin"
    ],
    methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS],
    allowedHeaders = ["Authorization", "Cache-Control", "Content-Type", "Access-Control-Allow-Headers", "X-Requested-With"]
)
@RestController
class ApiController(
    private val service: StorageService
) {
    @ApiOperation(value = "", tags = ["Manufacturers"])
    @GetMapping("/manufacturers")
    @ResponseStatus(HttpStatus.OK)
    fun loadManufacturers(): List<ManufacturerDto> =
        service.getAllManufacturers()

    @ApiOperation(value = "", tags = ["Manufacturers"])
    @PostMapping("/manufacturers")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("ADMIN")
    fun createManufacturer(
        @Valid @RequestBody createRequest: ManufacturerCreateRequestDto
    ): ManufacturerDto =
        service.addManufacturer(createRequest)

    @ApiOperation(value = "", tags = ["Manufacturers"])
    @PutMapping("/manufacturers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("ADMIN")
    fun updateManufacturer(
        @PathVariable("id") id: Long,
        @Valid @RequestBody updateRequest: ManufacturerUpdateRequestDto
    ) {
        service.updateManufacturer(id, updateRequest)
    }

    @ApiOperation(value = "", tags = ["Manufacturers"])
    @DeleteMapping("/manufacturers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("ADMIN")
    fun deleteManufacturer(
        @PathVariable("id") id: Long
    ) {
        service.deleteManufacturer(id)
    }

    @ApiOperation(value = "", tags = ["Pharmaceutical Forms"])
    @GetMapping("/pharmaceutical_forms")
    @ResponseStatus(HttpStatus.OK)
    fun loadPharmaceuticalForms(): List<PharmaceuticalFormDto> =
        service.getAllPharmaceuticalForms()

    @ApiOperation(value = "", tags = ["Pharmaceutical Forms"])
    @PostMapping("/pharmaceutical_forms")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("ADMIN")
    fun createPharmaceuticalForm(
        @Valid @RequestBody createRequest: PharmaceuticalFormCreateRequestDto
    ): PharmaceuticalFormDto =
        service.addPharmaceuticalForm(createRequest)

    @ApiOperation(value = "", tags = ["Pharmaceutical Forms"])
    @PutMapping("/pharmaceutical_forms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("ADMIN")
    fun updatePharmaceuticalForm(
        @PathVariable("id") id: Long,
        @Valid @RequestBody updateRequest: PharmaceuticalFormUpdateRequestDto
    ) {
        service.updatePharmaceuticalForm(id, updateRequest)
    }

    @ApiOperation(value = "", tags = ["Pharmaceutical Forms"])
    @DeleteMapping("/pharmaceutical_forms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("ADMIN")
    fun deletePharmaceuticalForm(
        @PathVariable("id") id: Long
    ) {
        service.deletePharmaceuticalForm(id)
    }

    @ApiOperation(value = "", tags = ["Series"])
    @GetMapping("/series")
    @ResponseStatus(HttpStatus.OK)
    fun loadSeries(): List<SeriesDto> =
        service.getAllSeries()

    @ApiOperation(value = "", tags = ["Series"])
    @PostMapping("/series")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("ADMIN")
    fun createSeries(
        @Valid @RequestBody createRequest: SeriesCreateRequestDto
    ): SeriesDto =
        service.addSeries(createRequest)

    @ApiOperation(value = "", tags = ["Series"])
    @PutMapping("/series/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("ADMIN")
    fun updateSeries(
        @PathVariable("id") id: Long,
        @Valid @RequestBody updateRequest: SeriesUpdateRequestDto
    ) {
        service.updateSeries(id, updateRequest)
    }

    @ApiOperation(value = "", tags = ["Series"])
    @DeleteMapping("/series/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("ADMIN")
    fun deleteSeries(
        @PathVariable("id") id: Long
    ) {
        service.deleteSeries(id)
    }

    @ApiOperation(value = "", tags = ["Products"])
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("ADMIN")
    fun createProduct(
        @Valid @RequestBody createRequest: ProductCreateRequestDto
    ): ProductDto =
        service.addProduct(createRequest)

    @ApiOperation(value = "", tags = ["Products"])
    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("ADMIN")
    fun updateProduct(
        @PathVariable("id") id: Long,
        @Valid @RequestBody updateRequest: ProductUpdateRequestDto
    ) {
        service.updateProduct(id, updateRequest)
    }

    @ApiOperation(value = "", tags = ["Products"])
    @GetMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun loadProduct(
        @PathVariable("id") id: Long
    ): ProductDto =
        service.getProduct(id)

    @ApiOperation(value = "", tags = ["Products"])
    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    fun loadProduct(): List<ProductDto> =
        service.getAllProducts()

    @ApiOperation(value = "", tags = ["Products"])
    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("ADMIN")
    fun deleteProduct(
        @PathVariable("id") id: Long
    ) {
        service.deleteProduct(id)
    }

    @ApiOperation(value = "", tags = ["Images"])
    @PostMapping("/images/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("ADMIN")
    fun createImage(
        @PathVariable("name") imageName: String,
        @RequestPart("file") file: MultipartFile
    ) {
        service.addImage(imageName, file.inputStream)
    }

    @ApiOperation(value = "", tags = ["Images"])
    @PutMapping("/images/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("ADMIN")
    fun updateImage(
        @PathVariable("name") imageName: String,
        @RequestPart("file") file: MultipartFile
    ) {
        service.updateImage(imageName, file.inputStream)
    }

    @ApiOperation(value = "", tags = ["Images"])
    @GetMapping(
        value = ["/images/{name}"],
        produces = [MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.TEXT_PLAIN_VALUE]
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun getImage(
        @PathVariable("name") imageName: String
    ): ByteArray = service.getImage(imageName)

    @ApiOperation(value = "", tags = ["Images"])
    @DeleteMapping("/images/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("ADMIN")
    fun deleteImage(
        @PathVariable("name") imageName: String
    ) {
        service.deleteImage(imageName)
    }

    @ApiOperation(value = "", tags = ["Users"])
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    fun createUser(
        @Valid @RequestBody createRequest: UserCreateRequestDto
    ) = service.addUser(createRequest)

    @ApiOperation(value = "", tags = ["Users"])
    @PutMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("USER")
    fun updateUser(
        session: HttpSession,
        @Valid @RequestBody updateRequest: UserUpdateRequestDto
    ) {
        session.getAttribute(SPRING_SECURITY_CONTEXT_KEY)?.let { securityContext ->
            (securityContext as SecurityContextImpl).authentication.principal.let { user ->
                service.updateUser((user as User).id, updateRequest)
            }
        }
    }

    @ApiOperation(value = "", tags = ["Users"])
    @GetMapping("/users/self")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RolesAllowed("USER")
    fun getUser(session: HttpSession) =
        getRawUser(session).toDto()

    @ApiOperation(value = "", tags = ["Users"])
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("USER")
    fun logout(request: HttpServletRequest?, response: HttpServletResponse?) {
        val auth: Authentication? = SecurityContextHolder.getContext().authentication
        if (auth != null) {
            SecurityContextLogoutHandler().logout(request, response, auth)
        }
    }

    @ApiOperation(value = "", tags = ["Users"])
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun login(
        session: HttpSession,
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ) = session.getAttribute(SPRING_SECURITY_CONTEXT_KEY)?.let { getUser(session) }

    @ApiOperation(value = "", tags = ["Users"])
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RolesAllowed("ADMIN")
    fun getUsers() =
        service.getAllUsers()

    @ApiOperation(value = "", tags = ["Cart"])
    @GetMapping("/cart")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RolesAllowed("USER")
    fun getUserCart(session: HttpSession): List<CartItemDto> =
        service.getUserCart(getRawUser(session).id)

    @ApiOperation(value = "", tags = ["Cart"])
    @PutMapping("/cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    @RolesAllowed("USER")
    fun updateUserCart(
        session: HttpSession,
        @Validated @RequestBody updateRequest: List<CartItemUpdateRequestDto>
    ) {
        service.updateUserCart(getRawUser(session).id, updateRequest)
    }

    @ApiOperation(value = "", tags = ["Order"])
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RolesAllowed("USER")
    fun createOrder(
        session: HttpSession,
        @Validated @RequestBody createRequestDto: OrderCreateRequestDto
    ) = service.addOrder(getRawUser(session).id, createRequestDto)

    @ApiOperation(value = "", tags = ["Order"])
    @PutMapping("/order/{order_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("ADMIN")
    fun updateOrder(
        @PathVariable("order_id") orderId: Long,
        @Validated @RequestBody updateRequestDto: OrderUpdateRequestDto
    ) {
        service.updateOrder(orderId, updateRequestDto)
    }

    @ApiOperation(value = "", tags = ["Order"])
    @GetMapping("/order/{order_id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RolesAllowed("USER")
    fun getOrder(
        session: HttpSession,
        @PathVariable("order_id") orderId: Long
    ): OrderDto =
        service.getOrder(orderId).also {
            val user = getRawUser(session)

            if (!user.authorities.contains(SimpleGrantedAuthority("ROLE_ADMIN")) && it.userId != user.id) {
                throw PermissionDenied()
            }
        }

    @ApiOperation(value = "", tags = ["Order"])
    @GetMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RolesAllowed("USER")
    fun getAllOrders(session: HttpSession): List<OrderDto> {
        val user = getRawUser(session)
        return if (user.authorities.contains(SimpleGrantedAuthority("ROLE_ADMIN"))) {
            service.getAllOrders()
        } else {
            service.getAllOrders(user.id)
        }
    }

    private fun getRawUser(session: HttpSession): User =
        session.getAttribute(SPRING_SECURITY_CONTEXT_KEY)!!.let { securityContext ->
            (securityContext as SecurityContextImpl).authentication.principal.let { user ->
                (user as User)
            }
        }
}