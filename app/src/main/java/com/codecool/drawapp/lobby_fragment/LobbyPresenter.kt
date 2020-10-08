package com.codecool.drawapp.lobby_fragment

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
            }
        })
        return theLobby
    }

    fun quitLobby(){
        listeners?.let{
            lobbyService.detachLobby(it.first,it.second)
            myLobby?.let { lobbyService.quitLobby(it) }
        }
        view.quitToMenu()
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
}