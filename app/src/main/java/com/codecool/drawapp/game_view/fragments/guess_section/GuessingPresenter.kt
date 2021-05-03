package com.codecool.drawapp.game_view.fragments.guess_section

import android.util.Log
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.data_layer.User
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueries
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueryService
import com.codecool.drawapp.dependency.lobby.LobbyService
import com.google.firebase.storage.StorageReference
import org.koin.core.KoinComponent
import org.koin.core.inject

class GuessingPresenter(val view : GuessingContractor) : KoinComponent{

    val basicDatabaseQueryService : BasicDatabaseQueryService by inject()
    val lobbyService : LobbyService by inject()

    fun filterWords(files : List<StorageReference>) {
        basicDatabaseQueryService.getMyUserFromDatabase(object : BasicDatabaseQueries.getMyUserFromDatabaseCallback{
            override fun onSuccess(user: User) {
                val x = files.filter{
                    (it.name.split("---")[0]) != user.userName
                }
                view.returnFilteredList(x)
            }

            override fun onFail() {
                Log.d("GuessingPresenter", "Error")
            }
        })
    }

    fun setDraw(storageReference: StorageReference) {
        storageReference.downloadUrl.addOnSuccessListener{
            Log.d("GuessingPresenter",it.toString())
            view.loadImage(it)
        }
    }

    fun voteWord(gameLobby: GameLobby, guessWord : String, originalWord: String) {
        lobbyService.addGuessWord(gameLobby, guessWord, originalWord)
        Log.d("GuessingPresenter", "Voted! original was $originalWord but you typed $guessWord")
    }

}