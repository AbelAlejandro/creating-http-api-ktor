package com.jetbrains.handson.httpapi.routes

import com.jetbrains.handson.httpapi.models.Order
import com.jetbrains.handson.httpapi.models.orderStorage
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*

fun Route.orderRouting() {
    route("/order") {
        get{
            if(orderStorage.isNotEmpty()) {
                call.respond(orderStorage)
            } else {
                call.respondText(
                    "No orders found",
                    status = HttpStatusCode.NotFound
                )
            }
        }
        get("{number}") {
            val number = call.parameters["number"] ?: return@get call.respondText(
                "Missing or malformed order #",
                status = HttpStatusCode.BadRequest
            )
            val order =
                orderStorage.find{ it.number == number } ?: return@get call.respondText(
                    "No order with number $number",
                    status = HttpStatusCode.NotFound
                )
            call.respond(order)
        }
        post {
            val order =  call.receive<Order>()
            orderStorage.add(order)
            call.respondText(
                "Order stored correctly",
                status = HttpStatusCode.Accepted
            )
        }
        delete("{number}") {
            val number = call.parameters["number"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if(orderStorage.removeIf { it.number == number }) {
                call.respondText("Order removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not found", status = HttpStatusCode.NotFound)
            }
        }
    }
}

fun Application.registerOrderRoutes() {
    routing {
        orderRouting()
    }
}