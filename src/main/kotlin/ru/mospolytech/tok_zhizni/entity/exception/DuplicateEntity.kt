package ru.mospolytech.tok_zhizni.entity.exception

open class DuplicateEntity: RuntimeException()

class DuplicateUserLogin: DuplicateEntity()

class DuplicateUserEmail: DuplicateEntity()

class DuplicateImageName: DuplicateEntity()