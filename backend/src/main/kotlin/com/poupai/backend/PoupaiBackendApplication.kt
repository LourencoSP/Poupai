package com.poupai.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PoupaiBackendApplication

fun main(args: Array<String>) {
    runApplication<PoupaiBackendApplication>(*args)
}