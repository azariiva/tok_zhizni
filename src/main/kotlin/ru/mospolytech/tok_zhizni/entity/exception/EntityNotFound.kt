package ru.mospolytech.tok_zhizni.entity.exception

open class EntityNotFound: RuntimeException()

class ManufacturerNotFound: EntityNotFound()

class PharmaceuticalFormNotFound: EntityNotFound()

class SeriesNotFound: EntityNotFound()

class ProductNotFound: EntityNotFound()

class UserNotFound: EntityNotFound()