package ru.mospolytech.tok_zhizni.rest.controller

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import ru.mospolytech.tok_zhizni.db.entity.Manufacturer
import ru.mospolytech.tok_zhizni.db.entity.ManufacturerCreateRequest
import ru.mospolytech.tok_zhizni.db.entity.ManufacturerUpdateRequest
import ru.mospolytech.tok_zhizni.service.TokZhizniService

@Suppress("SpellCheckingInspection")
@Controller
class TokZhizniStorageController(
    private val service: TokZhizniService
) {
    @GetMapping("/manufacturers")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun loadManufacturers(): List<Manufacturer> =
        service.getAllManufacturers()

    @PostMapping("/manufacturers")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun createManufacturer(
        @RequestBody createRequest: ManufacturerCreateRequest
    ): Manufacturer =
        service.addManufacturer(createRequest)

    @PutMapping("/manufacturers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateManufacturer(
        @PathVariable("id") id: Long,
        @RequestBody updateRequest: ManufacturerUpdateRequest
    ) {
        service.updateManufacturer(id, updateRequest)
    }
}