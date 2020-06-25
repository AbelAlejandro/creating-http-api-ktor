package com.jetbrains.handson.httpapi

import com.jetbrains.handson.httpapi.routes.registerEmployeeRoutes
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.serialization.json


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(CORS) {
        anyHost()
    }
    install(ContentNegotiation) {
        json()
    }
    registerEmployeeRoutes()
}
