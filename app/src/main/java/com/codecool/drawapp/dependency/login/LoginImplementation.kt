package com.codecool.drawapp.dependency.login

class LoginImplementation : LoginService {
    interface LogiCallback {}

    override fun onLogin(username: String, password: String, view: LogiCallback) {
        TODO("Not yet implemented")
    }
}