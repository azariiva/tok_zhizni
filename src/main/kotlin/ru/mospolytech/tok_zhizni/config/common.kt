package ru.mospolytech.tok_zhizni.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

val LOGGER = LoggerFactory.getLogger("Server")

@Configuration
class PasswordEncoderConfiguration {
    val passwordEncoder: PasswordEncoder
        @Bean get() = BCryptPasswordEncoder()
}