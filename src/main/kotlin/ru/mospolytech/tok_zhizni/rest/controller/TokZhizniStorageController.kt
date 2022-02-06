package ru.mospolytech.tok_zhizni.rest.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import ru.mospolytech.tok_zhizni.entity.*
import ru.mospolytech.tok_zhizni.service.TokZhizniService

@Api(value = "", tags = ["Storage Controller"])
@Suppress("SpellCheckingInspection")
@Controller
class TokZhizniStorageController(
    private val service: TokZhizniService
) {
    @ApiOperation(value = "", tags = ["Manufacturers"])
    @GetMapping("/manufacturers")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun loadManufacturers(): List<Manufacturer> =
        service.getAllManufacturers()

    @ApiOperation(value = "", tags = ["Manufacturers"])
    @PostMapping("/manufacturers")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun createManufacturer(
        @RequestBody createRequest: ManufacturerCreateRequest
    ): Manufacturer =
        service.addManufacturer(createRequest)

    @ApiOperation(value = "", tags = ["Manufacturers"])
    @PutMapping("/manufacturers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateManufacturer(
        @PathVariable("id") id: Long,
        @RequestBody updateRequest: ManufacturerUpdateRequest
    ) {
        service.updateManufacturer(id, updateRequest)
    }

    @ApiOperation(value = "", tags = ["Manufacturers"])
    @DeleteMapping("/manufacturers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteManufacturer(
        @PathVariable("id") id: Long
    ) {
        service.deleteManufacturer(id)
    }

    @ApiOperation(value = "", tags = ["Pharmaceutical Forms"])
    @GetMapping("/pharmaceutical_forms")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun loadPharmaceuticalForms(): List<PharmaceuticalForm> =
        service.getAllPharmaceuticalForms()

    @ApiOperation(value = "", tags = ["Pharmaceutical Forms"])
    @PostMapping("/pharmaceutical_forms")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun createPharmaceuticalForm(
        @RequestBody createRequest: PharmaceuticalFormCreateRequest
    ): PharmaceuticalForm =
        service.addPharmaceuticalForm(createRequest)

    @ApiOperation(value = "", tags = ["Pharmaceutical Forms"])
    @PutMapping("/pharmaceutical_forms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updatePharmaceuticalForm(
        @PathVariable("id") id: Long,
        @RequestBody updateRequest: PharmaceuticalFormUpdateRequest
    ) {
        service.updatePharmaceuticalForm(id, updateRequest)
    }

    @ApiOperation(value = "", tags = ["Pharmaceutical Forms"])
    @DeleteMapping("/pharmaceutical_forms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePharmaceuticalForm(
        @PathVariable("id") id: Long
    ) {
        service.deletePharmaceuticalForm(id)
    }

    @ApiOperation(value = "", tags = ["Series"])
    @GetMapping("/series")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun loadSeries(): List<Series> =
        service.getAllSeries()

    @ApiOperation(value = "", tags = ["Series"])
    @PostMapping("/series")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun createSeries(
        @RequestBody createRequest: SeriesCreateRequest
    ): Series =
        service.addSeries(createRequest)

    @ApiOperation(value = "", tags = ["Series"])
    @PutMapping("/series/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateSeries(
        @PathVariable("id") id: Long,
        @RequestBody updateRequest: SeriesUpdateRequest
    ) {
        service.updateSeries(id, updateRequest)
    }

    @ApiOperation(value = "", tags = ["Series"])
    @DeleteMapping("/series/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSeries(
        @PathVariable("id") id: Long
    ) {
        service.deleteSeries(id)
    }

    @ApiOperation(value = "", tags = ["Products"])
    @PostMapping("/products")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun createProduct(
        @RequestBody createRequest: ProductCreateRequest
    ): Product =
        service.addProduct(createRequest)

    @ApiOperation(value = "", tags = ["Products"])
    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateProduct(
        @PathVariable("id") id: Long,
        @RequestBody updateRequest: ProductUpdateRequest
    ) {
        service.updateProduct(id, updateRequest)
    }

    @ApiOperation(value = "", tags = ["Products"])
    @GetMapping("/products/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun loadProduct(
        @PathVariable("id") id: Long
    ): Product =
        service.getProduct(id)

    @ApiOperation(value = "", tags = ["Products"])
    @GetMapping("/products")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun loadProduct(): List<Product> =
        service.getAllProducts()

    @ApiOperation(value = "", tags = ["Products"])
    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(
        @PathVariable("id") id: Long
    ) {
        service.deleteProduct(id)
    }
}