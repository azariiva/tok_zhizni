package ru.mospolytech.tok_zhizni.config.validation.email

import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class EmailValidator : ConstraintValidator<ValidEmail, String> {
    companion object {
        private val EMAIL_PATTERN: String =
            "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$"
    }

    private lateinit var pattern: Pattern
    private lateinit var matcher: Matcher

    override fun initialize(constraintAnnotation: ValidEmail?) {}

    override fun isValid(email: String?, context: ConstraintValidatorContext?): Boolean =
        email?.let { validateEmail(it) } ?: true

    private fun validateEmail(email: String): Boolean {
        pattern = Pattern.compile(EMAIL_PATTERN)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }
}