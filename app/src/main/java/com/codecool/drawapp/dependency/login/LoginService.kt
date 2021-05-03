package com.codecool.drawapp.dependency.login

interface LoginService {
    fun onLogin(username : String, password : String, view : LoginImplementation.LoginCallback)
}