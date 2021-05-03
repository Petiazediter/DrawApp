package com.codecool.drawapp.dependency.lobby.lobby_listener

import com.codecool.drawapp.data_layer.GameLobby

interface LobbyListenerService {
    fun attachView ( view : LobbyListener, lobby : GameLobby)
    fun detachView ()
}