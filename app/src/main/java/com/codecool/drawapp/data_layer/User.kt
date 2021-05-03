package com.codecool.drawapp.data_layer

class User() {
    lateinit var userId : String
    lateinit var userName : String
    lateinit var emailAdress : String
    var isFriendRequestsEnabled : Boolean = true

    constructor (userId : String,userName : String, emailAdress : String, isFriendRequestsEnabled : Boolean) : this(){
        this.userId = userId
        this.userName = userName
        this.emailAdress = emailAdress
        this.isFriendRequestsEnabled = isFriendRequestsEnabled
    }
}