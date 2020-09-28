package com.codecool.drawapp.dependency.lobby

import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.User
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueries
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueryService
import org.koin.core.KoinComponent
import org.koin.core.inject

class LobbyImplementation : LobbyService,KoinComponent {
    val basicDatabaseQueryService : BasicDatabaseQueryService by inject()

    interface GameCallback{
        fun onFail()
        fun onSuccess()
    }

    override fun createGame(view: GameCallback) {
        basicDatabaseQueryService.getMyUserFromDatabase(object : BasicDatabaseQueries.getMyUserFromDatabaseCallback{
            override fun onFail() {
                view.onFail()
            }

            override fun onSuccess(user: User) {
                val gamesReference = ProjectDatabase.FIREBASE_DB.getReference("games")
                val newGameId = gamesReference.push().key.toString()
                val newGame = GameLobby(newGameId, user.userId, listOf(user),0)
                gamesReference.child(newGameId).setValue(newGame).addOnCompleteListener {
                    if ( it.isSuccessful) view.onSuccess()
                    else view.onFail()
                }
            }
        })
    }

}