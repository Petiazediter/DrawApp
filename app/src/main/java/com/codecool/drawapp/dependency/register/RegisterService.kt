package com.codecool.drawapp.dependency.register

interface RegisterService {
    fun onRegister ( username : String, password : String, email : String, view : RegisterImplementation.RegisterCallback)
}