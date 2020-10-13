package com.codecool.drawapp.game_view.fragments.wait_section

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codecool.drawapp.MainActivity
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.dependency.lobby.lobby_listener.LobbyListener

class WaitingForOthersFragment : Fragment() , WaitingContractor{

    private lateinit var presenter: WaitingPresenter
    private var lobby : GameLobby? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        presenter = WaitingPresenter(this)
        return inflater.inflate(R.layout.fragment_waiting, container, false)
    }

    public fun setGameLobby(gameLobby: GameLobby){
        lobby = gameLobby
    }
}