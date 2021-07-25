package com.hp.kotlinmvc

import org.modelmapper.ModelMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@SpringBootApplication
@Configuration
open class KotlinMvcApplication

fun main(args: Array<String>) {
	runApplication<KotlinMvcApplication>(*args)
}

@Bean
fun modelMapper(): ModelMapper {
	return ModelMapper()
}