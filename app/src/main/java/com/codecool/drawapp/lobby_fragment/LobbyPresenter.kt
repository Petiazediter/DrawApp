package com.codecool.drawapp.lobby_fragment

import android.util.Log
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.dependency.lobby.LobbyImplementation
import com.codecool.drawapp.dependency.lobby.LobbyService
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import org.koin.core.KoinComponent
import org.koin.core.inject

class LobbyPresenter(val view : LobbyContractor) : KoinComponent {
    val lobbyService : LobbyService by inject()
    var myLobby : GameLobby? = null
    var listeners : Pair<DatabaseReference, ValueEventListener>? = null

    fun createGame(){
        lobbyService.createGame(object : LobbyImplementation.GameCallback{
            override fun onFail() {
                view.onFail()
            }

            override fun onSuccess(gameLobby: GameLobby) {
                view.onSuccess(gameLobby)
                listeners = attachLobby(gameLobby)
            }
        })
    }

    private fun attachLobby (gameLobby : GameLobby) : Pair<DatabaseReference,ValueEventListener> {
        val theLobby : Pair<DatabaseReference, ValueEventListener> = lobbyService.attachLobby(gameLobby.gameId, object : LobbyImplementation.GameUpdateCallback{
            override fun onLobbyChange(lobby: GameLobby) {
                myLobby = lobby
                if (gameLobby.gameLeader !in gameLobby.players.map { it.userId }) {
                    quitLobby()
                }
                if ( lobby.round == 0) view.onSuccess(lobby)
                else {
                    view.moveToGameView(lobby)
                    listeners?.let{
                        lobbyService.detachLobby(it.first,it.second)
                    }
                }
            }

            override fun onLobbyDeleted() {
                quitLobby()
                view.onFail()
            }
        })
        return theLobby
    }

    fun quitLobby(){
        listeners?.let{
            lobbyService.detachLobby(it.first,it.second)
            myLobby?.let { lobbyService.quitLobby(it) }
        }
        myLobby = null
        listeners = null
    }

    fun joinGame ( lobbyId : String ){
        lobbyService.joinLobby(lobbyId,object : LobbyImplementation.JoinLobbyCallback{
            override fun onError() {
                view.onFail()
            }

            override fun onSuccess(gameLobby: GameLobby) {
                view.onSuccess(gameLobby)
                listeners = attachLobby(gameLobby)
            }
        })
    }

    fun startGame(){
        myLobby?.let{lobby ->
            lobbyService.startLobby(lobby,object : LobbyImplementation.StartLobbyCallback{
                override fun onError(errorMsg: String) {
                    Log.d("LobbyPresenter", "startGame() -> Error (# $errorMsg )")
                }

                override fun onSuccess() {
                    Log.d("LobbyPresenter", "startGame() -> Success!")
                    Log.d("LobbyPresenter", "startGame() -> LobbyRound : ${lobby.round}")
                }
            })
        } ?: run {
            Log.d("LobbyPresenter", "Error: No lobby!")
        }
    }
}