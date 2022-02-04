package ru.mospolytech.tok_zhizni.rest.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import ru.mospolytech.tok_zhizni.service.exception.EntityNotFound

@ControllerAdvice
class ErrorControllerAdvice {
    @ExceptionHandler(value = [EntityNotFound::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun entityNotFound(ex: EntityNotFound) {}
}