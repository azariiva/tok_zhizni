package ru.mospolytech.tok_zhizni.service

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.mospolytech.tok_zhizni.repository.UsersRepository

@Service
class UserDetailsServiceImpl(
    private val repository: UsersRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {
    override fun loadUserByUsername(username: String): ru.mospolytech.tok_zhizni.entity.domain.User =
        (repository.findByName(username) ?: throw UsernameNotFoundException("")).let {
            it.copy(aPassword = passwordEncoder.encode(it.aPassword))
        }
}