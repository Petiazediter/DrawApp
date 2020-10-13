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

class GuessingFragment(var files : List<StorageReference>, var gameLobby: GameLobby) : Fragment(), GuessingContractor {

    data class WordToGuess(val username : String, val word : String)

    lateinit var presenter: GuessingPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        presenter = GuessingPresenter(this)
        return inflater.inflate(R.layout.fragment_guessing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.filterWords(files)
    }

    override fun returnFilteredList(list: List<StorageReference>) {
        val names = list.map{ storageReference ->
            val l = storageReference.name.split("---")
            Log.d("GuessingFragment", "username : ${l[0]} | word : ${l[1]}")
            WordToGuess(l[0],l[1])}

        Log.d("GuessingFragment", "Filtered list returned : ${names.size}")
    }
}