package com.codecool.drawapp.dependency.lobby

import com.codecool.drawapp.data_layer.GameLobby
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

interface LobbyService {
    fun createGame(view : LobbyImplementation.GameCallback)
    fun attachLobby (lobbyId : String, view : LobbyImplementation.GameUpdateCallback): Pair<DatabaseReference, ValueEventListener>
    fun detachLobby(lobbyRow: DatabaseReference, valueEventListener: ValueEventListener)
    fun quitLobby(lobby: GameLobby)
    fun joinLobby ( lobbyId : String, view : LobbyImplementation.JoinLobbyCallback)
}