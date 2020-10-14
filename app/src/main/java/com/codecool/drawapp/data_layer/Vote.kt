package com.codecool.drawapp.data_layer

class Vote() {
    lateinit var userName : String
    lateinit var guessedWord : String
    var isTheOriginal : Boolean = false

    constructor(userName : String, guessedWord : String, isTheOriginal : Boolean) : this(){
        this.userName = userName
        this.guessedWord = guessedWord
        this.isTheOriginal = isTheOriginal
    }
}