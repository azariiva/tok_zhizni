package ru.mospolytech.tok_zhizni.entity.domain

import org.joda.time.DateTime
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.mospolytech.tok_zhizni.entity.dto.UserCreateRequestDto
import ru.mospolytech.tok_zhizni.entity.dto.UserUpdateRequestDto

data class User(
    val id: Long,
    val name: String,
    val aPassword: String,
    val email: String,
    val isVerified: Boolean,
    val registered: DateTime
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> =
        if (name == "admin") {
            listOf(SimpleGrantedAuthority("ROLE_ADMIN"), SimpleGrantedAuthority("ROLE_USER"))
        } else {
            listOf(SimpleGrantedAuthority("ROLE_USER"))
        }

    override fun getPassword(): String = aPassword

    override fun getUsername(): String = name

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}

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
