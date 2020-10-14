package com.codecool.drawapp.game_view

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.codecool.drawapp.api.RandomWord
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.dependency.lobby.LobbyImplementation
import com.codecool.drawapp.dependency.lobby.LobbyService
import com.codecool.drawapp.dependency.lobby.lobby_listener.LobbyListener
import com.codecool.drawapp.dependency.lobby.lobby_listener.LobbyListenerService
import com.codecool.drawapp.dependency.random_word.RandomWordImplementation
import com.codecool.drawapp.dependency.random_word.RandomWordService
import com.codecool.drawapp.dependency.upload_image.UploadService
import org.koin.core.KoinComponent
import org.koin.core.inject

 class GamePresenter (val view : GameContractor) : KoinComponent {
    private val apiService : RandomWordService by inject()
    private val lobbyListenerService : LobbyListenerService by inject()
    private val lobbyService : LobbyService by inject()
    var gameLobby : GameLobby? = null

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

    fun onQuit(quit : Boolean) {
        gameLobby?.let{lobbyService.quitLobby(it)}
        if (quit) view.backToMenu()
    }

    fun unAttach(){
        lobbyListenerService.detachView()
    }

    fun attachToListener(view_ : LobbyListener, lobby : String){
        Log.d("GamePresenter", "attachToListener() -> $lobby")
        lobbyService.findLobbyById(lobby, object : LobbyImplementation.FindLobbyCallback{
            override fun onCompleted(lobby: GameLobby?) {
                lobby?.let{
                    gameLobby = it
                    lobbyListenerService.attachView(view_,it)
                } ?: run{
                    view.backToMenu()
                }
            }
        })
    }

     fun createListToUsersWord(word: String){
         gameLobby?.let{ lobbyService.addGuessWord(it, word,true) }
     }
 }