package com.codecool.drawapp.data_layer

class Vote() {
    lateinit var userName : String
    lateinit var guessedWord : String
    lateinit var usersVoted : List<User>

    constructor(userName : String, guessedWord : String, usersVoted : List<User>) : this(){
        this.userName = userName
        this.guessedWord = guessedWord
        this.usersVoted = usersVoted
    }
}