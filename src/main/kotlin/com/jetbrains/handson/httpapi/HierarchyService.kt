package com.jetbrains.handson.httpapi

import com.jetbrains.handson.httpapi.models.Employee
import com.jetbrains.handson.httpapi.models.managersStorage
import java.util.*

fun processHierarchy() {
    val visited = HashSet<Employee>()
    dfs(managersStorage, visited, "", "")
}

fun dfs (
    managersStorage: MutableMap<Employee, MutableSet<Employee>>,
    visited: HashSet<Employee>,
    managerName: String,
    space: String
) {
    for(e: Employee? in managersStorage.getOrDefault(Employee(managerName), HashSet<Employee?>())) {
        if(e !in visited) {
            if (e != null) {
                println(space + e.name)
                visited.add(e)
                dfs(managersStorage, visited, e.name, " ")
            }
        }
    }
}