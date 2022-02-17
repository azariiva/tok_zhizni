package ru.mospolytech.tok_zhizni.config.validation.password

import javax.validation.Constraint

@Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordMatchesValidator::class])
@MustBeDocumented
annotation class PasswordMatches