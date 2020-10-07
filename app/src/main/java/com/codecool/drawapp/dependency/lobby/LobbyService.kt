package com.codecool.drawapp.dependency.lobby

import com.codecool.drawapp.data_layer.GameLobby

interface LobbyService {
    fun createGame(view : LobbyImplementation.GameCallback)
}