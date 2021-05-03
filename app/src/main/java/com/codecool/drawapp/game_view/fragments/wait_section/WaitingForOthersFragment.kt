package com.codecool.drawapp.game_view.fragments.wait_section

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.codecool.drawapp.MainActivity
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.dependency.lobby.lobby_listener.LobbyListener
import com.codecool.drawapp.game_view.GameView
import com.codecool.drawapp.game_view.GameViewInterface
import com.google.firebase.storage.StorageReference
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.loading_view.*

class WaitingForOthersFragment(private var gameLobby: GameLobby, private val gameView: GameView): Fragment() , WaitingContractor, GameViewInterface{

    private lateinit var presenter: WaitingPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_waiting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = WaitingPresenter(this)
        loading_bar.visibility = View.VISIBLE
        loading_bar.startRippleAnimation()
        Toasty.info(requireContext(), "Let's wait for the others :)", Toasty.LENGTH_SHORT).show()
        presenter.isLobbyReady(gameLobby)
    }

    override fun onLobbyChange(lobby: GameLobby) {
        gameLobby = lobby
        presenter.gameLobby = lobby
    }

    override fun allFilesLoaded(files: List<StorageReference>){
       gameView.onAllFilesLoaded(files)
    }
}