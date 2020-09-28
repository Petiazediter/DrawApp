package com.codecool.drawapp.dependency.lobby

interface LobbyService {
    fun createGame(view : LobbyImplementation.GameCallback)
}