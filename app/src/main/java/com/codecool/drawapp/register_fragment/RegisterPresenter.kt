package com.codecool.drawapp.register_fragment

import com.codecool.drawapp.dependency.register.RegisterImplementation
import com.codecool.drawapp.dependency.register.RegisterService
import org.koin.core.KoinComponent
import org.koin.core.inject

class RegisterPresenter(val view : RegisterContractor) : KoinComponent {

    val registerService : RegisterService by inject()

    fun attemptRegister(username : String, email : String, password1 : String, password2 : String) {
        if ( username.length > 4 && email.length > 4 && password1.length > 4 && password2.length > 4 && password1 == password2) {
            registerService.onRegister(username = username, email = email, password = password1, view = object : RegisterImplementation.RegisterCallback{
                override fun onError(errorMessage: String) {
                    view.onError(errorMessage)
                }
                override fun onSuccess() {
                    view.onSuccess()
                }
            })
        }else view.onError("Invalid datas!")
    }

}