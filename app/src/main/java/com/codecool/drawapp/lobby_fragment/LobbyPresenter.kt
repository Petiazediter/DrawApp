package com.codecool.drawapp.lobby_fragment

import com.codecool.drawapp.dependency.lobby.LobbyImplementation
import com.codecool.drawapp.dependency.lobby.LobbyService
import org.koin.core.KoinComponent
import org.koin.core.inject

class LobbyPresenter(val view : LobbyContractor) : KoinComponent {
    val lobbyService : LobbyService by inject()

    fun createGame(){
        lobbyService.createGame(object : LobbyImplementation.GameCallback{
            override fun onFail() {
                view.onFail()
            }

            override fun onSuccess() {
                view.onSuccess()
            }
        })
    }
}