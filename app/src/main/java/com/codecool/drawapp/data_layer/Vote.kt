package com.codecool.drawapp.data_layer

class Vote() {
    lateinit var userName : String
    lateinit var guessedWord : String

    constructor(userName : String, guessedWord : String) : this(){
        this.userName = userName
        this.guessedWord = guessedWord
    }
}