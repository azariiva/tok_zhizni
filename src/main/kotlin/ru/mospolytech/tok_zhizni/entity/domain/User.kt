package ru.mospolytech.tok_zhizni.entity.domain

import org.joda.time.DateTime
import ru.mospolytech.tok_zhizni.entity.dto.UserCreateRequestDto
import ru.mospolytech.tok_zhizni.entity.dto.UserUpdateRequestDto

data class User(
    val id: Long,
    val name: String,
    val password: String,
    val email: String,
    val isVerified: Boolean,
    val registered: DateTime
)

data class UserCreateRequest(
    val name: String,
    val password: String,
    val email: String
) {
    constructor(dto: UserCreateRequestDto) : this(dto.login!!, dto.password!!, dto.email!!)
}

data class UserUpdateRequest(
    val name: String?,
    val password: String?,
    val email: String?,
    val isVerified: Boolean?,
    val registered: DateTime?
) {
    constructor(dto: UserUpdateRequestDto) : this(dto.login, dto.password, dto.email, null, null)
}
