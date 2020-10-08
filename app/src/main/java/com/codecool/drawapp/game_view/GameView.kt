package com.codecool.drawapp.game_view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.GameLobby

class GameView : Fragment() {

    lateinit var presenter : GamePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{
            val gameLobby = it.getString("gameId")
            gameLobby?.let{
                Log.d("GameView", "onViewCreated() -> Lobby ID : $it")
            } ?: run { findNavController().navigate(R.id.action_gameView_to_lobbyFragment)}
        } ?: run {findNavController().navigate(R.id.action_gameView_to_lobbyFragment)}
    }
}