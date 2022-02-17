package ru.mospolytech.tok_zhizni.repository.exposed

import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.mospolytech.tok_zhizni.entity.domain.User
import ru.mospolytech.tok_zhizni.entity.domain.UserCreateRequest
import ru.mospolytech.tok_zhizni.entity.domain.UserUpdateRequest
import ru.mospolytech.tok_zhizni.repository.UsersRepository
import ru.mospolytech.tok_zhizni.repository.exposed.table.UsersTable
import ru.mospolytech.tok_zhizni.repository.security.CryptoLayer

@Repository
class UsersRepositoryExposedImpl(
    private val cryptoLayer: CryptoLayer
) : UsersRepository {
    @Transactional(readOnly = true)
    override fun findByName(name: String): User? =
        UsersTable
            .select { UsersTable.name eq name }
            .takeIf { !it.empty() }?.first()?.toUser()

    @Transactional(readOnly = true)
    override fun findByEmail(email: String): User? =
        UsersTable
            .select { UsersTable.email eq email }
            .takeIf { !it.empty() }?.first()?.toUser()

    @Transactional(readOnly = true)
    override fun find(): List<User> =
        UsersTable
            .selectAll()
            .map { it.toUser() }

    @Transactional(readOnly = true)
    override fun find(id: Long): User? =
        UsersTable
            .select { UsersTable.id eq id }
            .takeIf { !it.empty() }?.first()?.toUser()

    @Transactional
    override fun create(createRequest: UserCreateRequest): User =
        UsersTable
            .insert { body ->
                body[name] = createRequest.name
                body[email] = createRequest.email
                body[password] = cryptoLayer.encode(createRequest.password)
            }
            .resultedValues!!.first().toUser()

    @Transactional
    override fun update(id: Long, updateRequest: UserUpdateRequest) {
        UsersTable
            .update({ UsersTable.id eq id }) { body ->
                updateRequest.name?.let { body[name] = it }
                updateRequest.password?.let { body[password] = cryptoLayer.encode(it) }
                updateRequest.email?.let { body[email] = it }
                updateRequest.isVerified?.let { body[isVerified] = it }
                updateRequest.registered?.let { body[registered] = it }
            }
    }

    @Transactional
    override fun delete(id: Long) {
        UsersTable
            .deleteWhere { UsersTable.id eq id }
    }

    private fun ResultRow.toUser(): User =
        User(
            id = get(UsersTable.id).value,
            name = get(UsersTable.name),
            aPassword = cryptoLayer.decode(get(UsersTable.password)),
            email = get(UsersTable.email),
            isVerified = get(UsersTable.isVerified),
            registered = get(UsersTable.registered)
        )
}