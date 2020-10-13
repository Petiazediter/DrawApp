package com.codecool.drawapp.game_view.fragments.wait_section

import android.util.Log
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.data_layer.ProjectDatabase
import org.koin.core.KoinComponent
import org.koin.core.inject

class WaitingPresenter(val view : WaitingContractor) : KoinComponent {

    var gameLobby : GameLobby? = null

    fun isLobbyReady(gameLobby: GameLobby){
        this.gameLobby = gameLobby
        finishChecker()
    }

    private fun finishChecker(){
        this.gameLobby?.let{lobby ->
            ProjectDatabase.FIREBASE_STORAGE.getReference("${lobby.gameId}/${lobby.round}")
                .listAll().addOnCompleteListener{
                    it.result?.let{files ->
                        Log.d("WaitingPresenter", "There's ${files.items.size} file")
                        if (lobby.players.size != files.items.size){
                            finishChecker()
                        } else {
                            Log.d("WaitingPresenter", "All files uploaded :) (${lobby.players.size}/${lobby.players.size}")
                        }
                    }
                }
        }
    }
}
