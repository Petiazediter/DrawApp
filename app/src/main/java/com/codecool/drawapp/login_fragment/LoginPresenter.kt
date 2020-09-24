package com.codecool.drawapp.login_fragment

import com.codecool.drawapp.dependency.login.LoginImplementation
import com.codecool.drawapp.dependency.login.LoginService
import org.koin.core.KoinComponent
import org.koin.core.inject

class LoginPresenter( val view : LoginContractor) : KoinComponent  {
     val loginService : LoginService by inject()

    fun onLogin(userName : String, password : String ){
        loginService.onLogin(userName, password, object : LoginImplementation.LoginCallback{
            override fun onError(errorMsg: String) {
                view.onError(errorMsg)
            }

            override fun onSuccess() {
                view.onSuccess()
            }
        })
    }

}