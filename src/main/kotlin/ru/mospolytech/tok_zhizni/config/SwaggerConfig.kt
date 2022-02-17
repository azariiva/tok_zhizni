package ru.mospolytech.tok_zhizni.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession


@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun api(): Docket =
        Docket(DocumentationType.SWAGGER_2)
            .ignoredParameterTypes(HttpSession::class.java, HttpServletRequest::class.java, HttpServletResponse::class.java)
            .select()
            .apply {
                apis(RequestHandlerSelectors.basePackage("ru.mospolytech.tok_zhizni.rest.controller"))
                paths(PathSelectors.any())
            }.build()
}