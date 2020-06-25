package com.jetbrains.handson.httpapi.models

import kotlinx.serialization.Serializable

val managersStorage = mutableMapOf<Employee, MutableSet<Employee>>()

@Serializable
data class Employee (val name: String, val managerName : String? = null)