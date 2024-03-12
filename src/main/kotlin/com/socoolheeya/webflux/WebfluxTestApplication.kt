package com.socoolheeya.webflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.socoolheeya.webflux"])
class WebfluxTestApplication

fun main(args: Array<String>) {
    runApplication<WebfluxTestApplication>(*args)
}
