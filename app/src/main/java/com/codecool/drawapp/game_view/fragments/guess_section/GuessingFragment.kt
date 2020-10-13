package com.codecool.drawapp.game_view.fragments.guess_section

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.GameLobby
import com.google.firebase.storage.StorageReference
import org.koin.core.KoinComponent

class GuessingFragment(var files : List<StorageReference>, var gameLobby: GameLobby) : Fragment() {


    data class WordToGuess(val username : String, val word : String)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guessing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val names = files.map{ storageReference ->
            val l = storageReference.name.split("---")
            Log.d("GuessingFragment", "username : ${l[0]} | word : ${l[1]}")
            WordToGuess(l[0],l[1])
        }
    }


}