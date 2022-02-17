package ru.mospolytech.tok_zhizni.repository.security

interface CryptoLayer {
    fun encode(toEncode: String): ByteArray
    fun decode(encoded: ByteArray): String
}