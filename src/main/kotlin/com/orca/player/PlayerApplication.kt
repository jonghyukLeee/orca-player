package com.orca.player

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class PlayerApplication

suspend fun main(args: Array<String>) {
	runApplication<PlayerApplication>(*args)
}
