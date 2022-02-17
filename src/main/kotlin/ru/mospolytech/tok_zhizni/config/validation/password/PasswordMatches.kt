package ru.mospolytech.tok_zhizni.config.validation.password

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordMatchesValidator::class])
@MustBeDocumented
annotation class PasswordMatches(
    val message: String = "Passwords don't match",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
