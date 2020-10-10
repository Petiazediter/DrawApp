package com.codecool.drawapp.lobby_fragment

import com.codecool.drawapp.data_layer.GameLobby

interface LobbyContractor {
    fun onFail()
    fun onSuccess(gameLobby: GameLobby)
    fun changeLobby ( gameLobby: GameLobby)
    fun moveToGameView(gameLobby: GameLobby)

    fun showError( message : String)
    fun showSuccess(message : String)
}