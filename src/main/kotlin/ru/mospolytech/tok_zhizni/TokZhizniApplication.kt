package ru.mospolytech.tok_zhizni

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication


@Suppress("SpellCheckingInspection")
@SpringBootApplication
@EnableConfigurationProperties
class TokZhizniApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<TokZhizniApplication>(*args)
        }
    }
}