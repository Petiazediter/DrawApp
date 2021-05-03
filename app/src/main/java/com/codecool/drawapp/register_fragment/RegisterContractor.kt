package com.codecool.drawapp.register_fragment

interface RegisterContractor {
    fun onError(errorMessage: String)
    fun onSuccess()
}