package com.codecool.drawapp.lobby_fragment

import android.util.Log
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.dependency.lobby.LobbyImplementation
import com.codecool.drawapp.dependency.lobby.LobbyService
import com.codecool.drawapp.dependency.lobby.lobby_listener.LobbyListener
import com.codecool.drawapp.dependency.lobby.lobby_listener.LobbyListenerService
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import org.koin.core.KoinComponent
import org.koin.core.inject

class LobbyPresenter(val view : LobbyContractor) : KoinComponent {

    val lobbyService : LobbyService by inject()
    val lobbyListenerService : LobbyListenerService by inject()
    var gameLobby : GameLobby? = null

    // Hosting a new game
    fun createGame(){
        lobbyService.createGame(object : LobbyImplementation.GameCallback{
            override fun onFail() {
                view.onFail()
            }

            override fun onSuccess(gameLobby: GameLobby) {
                view.onSuccess(gameLobby)
                this@LobbyPresenter.gameLobby = gameLobby
            }
        })
    }

    // Attaching the listener when the lobby joining is successful
    fun joinLobby(gameLobby: GameLobby, view : LobbyListener){
        lobbyListenerService.attachView(view,gameLobby)
    }

    fun unAttach(){
        lobbyListenerService.detachView()
    }

    fun quitFromLobby(destroyed : Boolean){
        gameLobby?.let{
            Log.d("LobbyPresenter", "onQuit() -> ${it.gameId}")
            lobbyService.quitLobby(it)} ?: run{Log.d("LobbyPresenter", "onQuit() -> No game!")}
        if ( !destroyed) {view.quitToMenu()}
    }

    // Join Game with the given Lobby ID
    fun joinGame ( lobbyId : String ){
        lobbyService.joinLobby(lobbyId,object : LobbyImplementation.JoinLobbyCallback{
            override fun onError() {
                view.onFail()
            }

            override fun onSuccess(gameLobby: GameLobby) {
                view.onSuccess(gameLobby)
                this@LobbyPresenter.gameLobby = gameLobby
            }
        })
    }

    // Pressing Start Game Button
    fun startGame(lobby : GameLobby){
        lobbyService.startLobby(lobby,object : LobbyImplementation.StartLobbyCallback{
            override fun onError(errorMsg: String) {
                Log.d("LobbyPresenter", "startGame() -> Error (# $errorMsg )")
                view.showError(errorMsg)
            }

            override fun onSuccess() {
                Log.d("LobbyPresenter", "startGame() -> Success!")
                view.showSuccess("Game Started!")
            }
        })
    }
}