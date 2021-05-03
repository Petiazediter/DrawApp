package com.codecool.drawapp.dependency.lobby.lobby_listener

import com.codecool.drawapp.data_layer.GameLobby

interface LobbyListener {
    fun requestQuitToMenu()
    fun onLobbyChange(lobby : GameLobby)
    fun onRoundChange(lobby : GameLobby)
}