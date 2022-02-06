package ru.mospolytech.tok_zhizni.rest.controller

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import ru.mospolytech.tok_zhizni.db.entity.*
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

    @DeleteMapping("/manufacturers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteManufacturer(
        @PathVariable("id") id: Long
    ) {
        service.deleteManufacturer(id)
    }

    @GetMapping("/pharmaceutical_forms")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun loadPharmaceuticalForms(): List<PharmaceuticalForm> =
        service.getAllPharmaceuticalForms()

    @PostMapping("/pharmaceutical_forms")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun createPharmaceuticalForm(
        @RequestBody createRequest: PharmaceuticalFormCreateRequest
    ): PharmaceuticalForm =
        service.addPharmaceuticalForm(createRequest)

    @PutMapping("/pharmaceutical_forms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updatePharmaceuticalForm(
        @PathVariable("id") id: Long,
        @RequestBody updateRequest: PharmaceuticalFormUpdateRequest
    ) {
        service.updatePharmaceuticalForm(id, updateRequest)
    }

    @DeleteMapping("/pharmaceutical_forms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePharmaceuticalForm(
        @PathVariable("id") id: Long
    ) {
        service.deletePharmaceuticalForm(id)
    }
}