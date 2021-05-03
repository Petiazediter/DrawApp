package com.codecool.drawapp.dependency.basic_queries

interface BasicDatabaseQueryService {
    fun getMyUserFromDatabase (view : BasicDatabaseQueries.getMyUserFromDatabaseCallback)
    fun getUserById ( userId : String, view : BasicDatabaseQueries.GetUserByIdCallback)
}