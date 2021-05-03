package com.codecool.drawapp.data_layer

class Vote() {
    var userName : String = "none"
    var guessedWord : String = "none"
    var usersVoted : List<User>? = null

    constructor(userName : String, guessedWord : String, usersVoted : List<User>?) : this(){
        this.userName = userName
        this.guessedWord = guessedWord
        this.usersVoted = usersVoted
    }

}