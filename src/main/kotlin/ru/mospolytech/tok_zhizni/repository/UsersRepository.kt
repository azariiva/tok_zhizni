package ru.mospolytech.tok_zhizni.repository

import ru.mospolytech.tok_zhizni.entity.domain.User
import ru.mospolytech.tok_zhizni.entity.domain.UserCreateRequest
import ru.mospolytech.tok_zhizni.entity.domain.UserUpdateRequest

interface UsersRepository: CrudRepository<UserCreateRequest, UserUpdateRequest, User> {
    fun findByName(name: String): User?
    fun findByEmail(email: String): User?
}