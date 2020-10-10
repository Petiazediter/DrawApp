package com.codecool.drawapp.game_view

import android.util.Log
import com.codecool.drawapp.api.RandomWord
import com.codecool.drawapp.dependency.random_word.RandomWordImplementation
import com.codecool.drawapp.dependency.random_word.RandomWordService
import org.koin.core.KoinComponent
import org.koin.core.inject

class GamePresenter ( val view : GameContractor) : KoinComponent {
    val apiService : RandomWordService by inject()
    fun getRandomWord() {
        Log.d("GamePresenter", "Call started!")
        apiService.getRandomWord(object : RandomWordImplementation.RandomWordCallback{
            override fun onSuccess(word: RandomWord) {
                view.getWord(word.name!!)
                Log.d("GamePresenter","Call finished!")
            }

            override fun onError() {
                Log.d("GamePresenter","Call finished!")
            }
        })
    }
}