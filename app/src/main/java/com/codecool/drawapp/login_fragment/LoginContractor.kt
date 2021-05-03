package com.codecool.drawapp.login_fragment

interface LoginContractor {
    fun onSuccess()
    fun onError(error : String)
}