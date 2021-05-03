package com.codecool.drawapp.dependency.random_word

interface RandomWordService {
    fun getRandomWord(view : RandomWordImplementation.RandomWordCallback)
}