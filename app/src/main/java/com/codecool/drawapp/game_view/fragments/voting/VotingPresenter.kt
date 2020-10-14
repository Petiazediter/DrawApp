package com.codecool.drawapp.game_view.fragments.voting

import android.util.Log
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.Vote
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueryService
import com.codecool.drawapp.dependency.lobby.LobbyImplementation
import com.codecool.drawapp.dependency.lobby.LobbyService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.ext.getScopeId

class VotingPresenter(val view : VotingContractor) : KoinComponent {

    val basicDatabaseQueryService : BasicDatabaseQueryService by inject()
    val lobbyService : LobbyService by inject()

    lateinit var gameLobby: GameLobby

    fun startLoading(gameLobby : GameLobby){
        this.gameLobby = gameLobby
        isEveryBodyGuessed()
        Log.d("VotingPresenter", "startLoading()")
    }

    private fun isEveryBodyGuessed(){
        Log.d("VotingPresenter", "Loading started again!")
        // Here we want to know everybody voted for every picture
        val votes = ProjectDatabase.FIREBASE_DB.getReference("games")
            .child(gameLobby.gameId)
            .child("votes")
            .child( gameLobby.round.toString())

        votes.addListenerForSingleValueEvent( object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if ( snapshot.exists()){
                    var sumOfVotes = 0
                    (snapshot.children).forEach{ originalWord ->// it -> originalWord  { user1 {guessed ...}, ...}
                        sumOfVotes += originalWord.children.toList().size
                    }
                    Log.d("VotingPresenter", "Summary of votes : $sumOfVotes")
                    if ( sumOfVotes != (gameLobby.players.size * gameLobby.players.size)) isEveryBodyGuessed()
                    else view.everyBodyGuessed()
                } else Log.d("VotingPresenter", "No such snapshot")
            }
        })
    }

    fun loadGuessings(){
        lobbyService.getGuessings(gameLobby, object : LobbyImplementation.GetGuessingsInterface{
            override fun callback(votes: List<Vote>) {

            }
        })
    }

}