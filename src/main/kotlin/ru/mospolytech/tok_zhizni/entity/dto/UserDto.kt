package ru.mospolytech.tok_zhizni.entity.dto

import org.joda.time.DateTime
import ru.mospolytech.tok_zhizni.config.validation.email.ValidEmail
import ru.mospolytech.tok_zhizni.config.validation.password.PasswordMatches
import ru.mospolytech.tok_zhizni.entity.domain.User
import javax.validation.constraints.NotEmpty

data class UserDto(
    val id: Long,
    val login: String,
    val email: String,
    val isVerified: Boolean,
    val registered: DateTime
)

@PasswordMatches
data class UserCreateRequestDto(
    @field:[NotEmpty]
    val login: String?,

    @field:[NotEmpty]
    val password: String?,
    val matchingPassword: String?,

    @field:[NotEmpty ValidEmail]
    val email: String?
)

@PasswordMatches
data class UserUpdateRequestDto(
    val login: String?,

    val password: String?,
    val matchingPassword: String?,

    @field:[ValidEmail]
    val email: String?
)

fun User.toDto(): UserDto =
    UserDto(
        id = id,
        login = name,
        email = email,
        isVerified = isVerified,
        registered = registered
    )