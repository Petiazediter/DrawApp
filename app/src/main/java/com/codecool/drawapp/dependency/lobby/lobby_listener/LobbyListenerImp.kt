package com.codecool.drawapp.dependency.lobby.lobby_listener

import android.util.Log
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.data_layer.ProjectDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class LobbyListenerImp : LobbyListenerService {
    companion object {
        private var myView : LobbyListener? = null
        private val lobbyTable = ProjectDatabase.FIREBASE_DB.getReference("games")
        private var myListener : ValueEventListener? = null
        private var myLobby : GameLobby? = null
    }

    override fun attachView(view: LobbyListener, lobby : GameLobby) {
        myView = view
        subscribeLobby(lobby)
    }

    override fun detachView() {
        myView = null
        unsubscribeLobby()
    }

    private fun subscribeLobby(lobby: GameLobby){
        // Set my Lobby to the given lobby
        myLobby = lobby

        // Set my listener to a new valueEventListener
        myListener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                myView?.requestQuitToMenu()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if ( !snapshot.exists()) myView?.requestQuitToMenu()
                else {
                    val theLobby = snapshot.getValue(GameLobby::class.java)
                    theLobby?.let{ currentLobby ->
                        // If the leader not in the match anymore
                        val leader = currentLobby.gameLeader
                        val players = currentLobby.players
                        if ( (leader !in players.map{it.userId}) || players.size == 1){
                            Log.d("LobbyListener", "onDataChange() -> request quit bc. 1 player || no leader")
                            myView?.requestQuitToMenu()
                        } else myView?.onLobbyChange(currentLobby)
                    }
                }
            }
        }
        // Attach the listener to the table
        myListener?.let{ lobbyTable.child(lobby.gameId).addValueEventListener(it) }
    }

    private fun unsubscribeLobby(){
        myListener?.let{listener ->
            myLobby?.let{lobby-> lobbyTable.child(lobby.gameId).removeEventListener(listener)}
        }
    }
}