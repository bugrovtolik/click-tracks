package com.abuhrov.my_band

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyBandApplication

fun main(args: Array<String>) {
    runApplication<MyBandApplication>(*args)
}
