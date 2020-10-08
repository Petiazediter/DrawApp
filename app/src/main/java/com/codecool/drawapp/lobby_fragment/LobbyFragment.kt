package com.codecool.drawapp.lobby_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.GameLobby
import kotlinx.android.synthetic.main.fragment_invites.*
import kotlinx.android.synthetic.main.fragment_invites.loading_bar
import kotlinx.android.synthetic.main.fragment_lobby.*


class LobbyFragment : Fragment(), LobbyContractor {

    lateinit var presenter: LobbyPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lobby, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LobbyPresenter(this)
        val lobbyId : String? = arguments?.getString("lobbyId")
        lobbyId?.let{
            Log.d("LobbyFragment()","lobbyId : $lobbyId")
            presenter.joinGame(it)
        } ?: run {
            Log.d("LobbyFragment", "Created()")
            presenter.createGame()
        }
    }

    override fun onFail() {
        // Failed to create a game
        findNavController().navigate(R.id.action_lobbyFragment_to_mainMenuFragment)
    }

    override fun onSuccess(gameLobby : GameLobby) {
        // Success game create
        loading_bar.visibility = View.INVISIBLE
        mid.visibility = View.VISIBLE
        max_players.visibility = View.VISIBLE

        room_code.visibility = View.VISIBLE
        room_code.text = "#"+gameLobby.gameId.substring(1,7)

        current_players.visibility = View.VISIBLE
        current_players.text = gameLobby.players.size.toString()

        friendsRecycler.visibility = View.VISIBLE
        startGame.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.quitLobby()
    }

    override fun changeLobby(gameLobby: GameLobby) {
        current_players.text = gameLobby.players.size.toString()
    }

    override fun quitToMenu() {
        findNavController().popBackStack()
    }
}