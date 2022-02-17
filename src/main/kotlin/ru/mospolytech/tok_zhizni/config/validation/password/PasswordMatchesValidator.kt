package ru.mospolytech.tok_zhizni.config.validation.password

import ru.mospolytech.tok_zhizni.entity.dto.UserCreateRequestDto
import ru.mospolytech.tok_zhizni.entity.dto.UserUpdateRequestDto
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext


class PasswordMatchesValidator : ConstraintValidator<PasswordMatches, Any> {
    override fun initialize(constraintAnnotation: PasswordMatches?) {}

    override fun isValid(obj: Any?, context: ConstraintValidatorContext?): Boolean =
        when (obj) {
            is UserCreateRequestDto -> obj.password == obj.matchingPassword
            is UserUpdateRequestDto -> obj.password == obj.matchingPassword
            else -> false
        }
}