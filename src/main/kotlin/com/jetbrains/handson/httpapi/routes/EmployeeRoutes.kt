package com.jetbrains.handson.httpapi.routes

import com.jetbrains.handson.httpapi.models.Employee
import com.jetbrains.handson.httpapi.models.managersStorage
import com.jetbrains.handson.httpapi.processHierarchy
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.features.ContentTransformationException
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing

fun Route.listOfEmployees() =
    post {
        var mapOfEmployees = mutableMapOf<String,String>()
        try {
            mapOfEmployees = call.receive()
        } catch (e: ContentTransformationException) {
            call.respondText(
                "The list of employees is not in a valid format",
                status = HttpStatusCode.BadRequest
            )
        }
        for((employeeName, managerName) in mapOfEmployees) {
            val manager = Employee(managerName)
            managersStorage.putIfAbsent(manager, mutableSetOf<Employee>())
            managersStorage[manager]?.add(Employee(employeeName, managerName))
        }
        processHierarchy()
        call.respondText(
            "Employees stored correctly",
            status = HttpStatusCode.Accepted
        )
    }

fun Route.getListOfEmployees() =
    get("/employees") {
        if(managersStorage.isNotEmpty()) {
            call.respond(managersStorage)
        }
    }

fun Application.registerEmployeeRoutes() {
    routing {
        listOfEmployees()
        getListOfEmployees()
    }
}