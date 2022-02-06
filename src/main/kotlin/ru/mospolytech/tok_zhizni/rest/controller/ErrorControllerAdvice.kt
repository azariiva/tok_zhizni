package ru.mospolytech.tok_zhizni.rest.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import ru.mospolytech.tok_zhizni.entity.exception.EntityNotFound

@ControllerAdvice
class ErrorControllerAdvice {
    @ExceptionHandler(value = [EntityNotFound::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun entityNotFound(ex: EntityNotFound): String = ex.toString()
}