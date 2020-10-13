package com.codecool.drawapp.game_view.fragments.guess_section

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.GameLobby
import com.google.firebase.storage.StorageReference

class GuessingFragment(val files : List<StorageReference>, var gameLobby: GameLobby) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guessing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}