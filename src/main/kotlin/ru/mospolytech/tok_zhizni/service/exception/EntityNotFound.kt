package ru.mospolytech.tok_zhizni.service.exception

open class EntityNotFound: RuntimeException()

class ManufacturerNotFound: EntityNotFound()

class PharmaceuticalFormNotFound: EntityNotFound()

class SeriesNotFound: EntityNotFound()