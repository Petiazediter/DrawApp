package com.codecool.drawapp.dependency.lobby

import android.util.Log
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.User
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueries
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueryService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import org.koin.core.KoinComponent
import org.koin.core.inject

class LobbyImplementation : LobbyService,KoinComponent {
    val basicDatabaseQueryService : BasicDatabaseQueryService by inject()

    interface GameCallback{
        fun onFail()
        fun onSuccess(gameLobby: GameLobby)
    }

    override fun createGame(view: GameCallback) {
        basicDatabaseQueryService.getMyUserFromDatabase(object : BasicDatabaseQueries.getMyUserFromDatabaseCallback{
            override fun onFail() {
                view.onFail()
            }

            override fun onSuccess(user: User) {
                val gamesReference = ProjectDatabase.FIREBASE_DB.getReference("games")
                val newGameId = gamesReference.push().key.toString()
                val newGame = GameLobby(newGameId, user.userId, arrayListOf(user),0)
                gamesReference.child(newGameId).setValue(newGame).addOnCompleteListener {
                    if ( it.isSuccessful) view.onSuccess(newGame)
                    else view.onFail()
                }
            }
        })
    }

    interface GameUpdateCallback {
        fun onLobbyChange(lobby : GameLobby)
    }

    override fun attachLobby(lobbyId : String, view: GameUpdateCallback) : Pair<DatabaseReference,ValueEventListener> {
        val lobbyRow = ProjectDatabase.FIREBASE_DB.getReference("games").child(lobbyId)
        val valueEventListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    Log.d("Changed", "CHANGED CHANGED CHANGED CHANGED CHANGED")
                    val gameLobby = snapshot.getValue(GameLobby::class.java)
                    gameLobby?.let { view.onLobbyChange(gameLobby) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                detachLobby(lobbyRow,this)
            }
        }
        lobbyRow.addValueEventListener(valueEventListener)
        return Pair(lobbyRow,valueEventListener)
    }

    override fun detachLobby(lobbyRow: DatabaseReference, valueEventListener: ValueEventListener) {
        lobbyRow.removeEventListener(valueEventListener)
        Log.d("detachLobby()", "Detached!")
    }

    override fun quitLobby (lobby: GameLobby) {
        basicDatabaseQueryService.getMyUserFromDatabase(object : BasicDatabaseQueries.getMyUserFromDatabaseCallback {
            override fun onFail() {
                TODO("Not yet implemented")
            }

            override fun onSuccess(mUser: User) {
                val lobbyRow = ProjectDatabase.FIREBASE_DB.getReference("games")
                val theGame = lobbyRow.child(lobby.gameId)
                theGame.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {}
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            snapshot.getValue(GameLobby::class.java)?.let {
                                it.players = it.players.filter { user -> user.userId != mUser.userId } as ArrayList<User>
                                theGame.setValue(it)
                                if ( it.gameLeader !in it.players.map{it.userId} ) theGame.removeValue()
                                else theGame.setValue(it)
                            }
                        }
                    }
                })
            }
        })
    }

    interface JoinLobbyCallback {
        fun onError()
        fun onSuccess(gameLobby: GameLobby)
    }

    override fun joinLobby(lobbyId: String, view : JoinLobbyCallback) {

        basicDatabaseQueryService.getMyUserFromDatabase(object : BasicDatabaseQueries.getMyUserFromDatabaseCallback{
            override fun onFail() {
                view.onError()
            }

            override fun onSuccess(user: User) {
                val games = ProjectDatabase.FIREBASE_DB.getReference("games")
                games.addListenerForSingleValueEvent( object  : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if ( snapshot.exists()){
                            val list = snapshot.children.filter{it.key?.substring(1,7)?.toLowerCase() == lobbyId.toLowerCase()}
                            if ( list.isNullOrEmpty()) view.onError()
                            else list[0].getValue(GameLobby::class.java)?.let{
                                if ( it.round == 0 && (user !in it.players)) {
                                    it.players.add(user)
                                    games.child(it.gameId).setValue(it)
                                    view.onSuccess(it)
                                } else view.onError()
                            } ?: run{view.onError()}
                        }else view.onError()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        view.onError()
                    }
                })
            }
        })
    }
}