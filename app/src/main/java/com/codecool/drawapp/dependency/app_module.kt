package com.codecool.drawapp.dependency

import com.codecool.drawapp.dependency.login.LoginImplementation
import com.codecool.drawapp.dependency.login.LoginService
import com.codecool.drawapp.dependency.register.RegisterImplementation
import com.codecool.drawapp.dependency.register.RegisterService
import org.koin.dsl.module

val appModule = module{
    single<RegisterService> { RegisterImplementation() }
    single<LoginService> { LoginImplementation() }
}