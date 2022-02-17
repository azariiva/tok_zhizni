package ru.mospolytech.tok_zhizni.repository.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.stereotype.Component

@Component
class CryptoLayerImpl(
    @Value("\${spring.datasource.password}") private val password: String
) : CryptoLayer {
    private val encryptor: TextEncryptor = Encryptors.text(password, "ffff")
    
    override fun encode(toEncode: String): ByteArray = encryptor.encrypt(toEncode).toByteArray(Charsets.UTF_8)
    
    override fun decode(encoded: ByteArray): String = encryptor.decrypt(encoded.toString(Charsets.UTF_8))
}