package com.codecool.drawapp.lobby_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.codecool.drawapp.MainActivity
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.dependency.lobby.lobby_listener.LobbyListener
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_invites.*
import kotlinx.android.synthetic.main.fragment_lobby.*
import kotlinx.android.synthetic.main.loading_view.*


class LobbyFragment : Fragment(), LobbyContractor, LobbyListener, MainActivity.BackButtonInterface {

    lateinit var presenter: LobbyPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lobby, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LobbyPresenter(this)
        val lobbyId : String? = arguments?.getString("lobbyId")
        lobbyId?.let{ presenter.joinGame(it) } ?: run {presenter.createGame()}
        loading_bar.startRippleAnimation()
        loading_bar.visibility = View.VISIBLE
    }

    override fun onFail() {
        // Failed to create a game or to join one
        findNavController().navigate(R.id.action_lobbyFragment_to_mainMenuFragment)
    }

    override fun onResume() {
        super.onResume()
        Log.d("LobbyFragment", "onResume()")
    }

    override fun onSuccess(gameLobby : GameLobby) {
        // Success game create
        presenter.joinLobby(gameLobby, this)

        loading_bar.visibility = View.INVISIBLE
        loading_bar.stopRippleAnimation()
        mid.visibility = View.VISIBLE
        max_players.visibility = View.VISIBLE

        room_code.visibility = View.VISIBLE
        room_code.text = "#"+gameLobby.gameId.substring(1,7)

        current_players.visibility = View.VISIBLE
        current_players.text = gameLobby.players.size.toString()

        friendsRecycler.visibility = View.VISIBLE

        startGame.visibility = View.VISIBLE
        updateStartGameButton(gameLobby)
    }

    private fun updateStartGameButton(gameLobby: GameLobby) {
        startGame.setOnClickListener {
            presenter.startGame(lobby = gameLobby)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unAttach()
    }

    override fun onBackButtonPressed() {
        presenter.quitFromLobby(true)
        Log.d ( "LobbyFragment", "onBackButtonPressed()")
    }

    override fun changeLobby(gameLobby: GameLobby) {
        current_players.text = gameLobby.players.size.toString()
        updateStartGameButton(gameLobby)
        if ( gameLobby.round > 0){
            moveToGameView(gameLobby)
        }
    }

    override fun moveToGameView(gameLobby: GameLobby) {
        val bundle = Bundle()
        bundle.putString("gameId", gameLobby.gameId)
        findNavController().navigate(R.id.action_lobbyFragment_to_gameView,bundle)
    }

    override fun showError(message: String) {
        Toasty.error(requireContext(), message, Toasty.LENGTH_SHORT).show()
    }

    override fun showSuccess(message: String) {
        Toasty.success(requireContext(), message, Toasty.LENGTH_SHORT).show()
    }

    override fun onLobbyChange(lobby: GameLobby) {
        changeLobby(lobby)
        Log.d("LobbyFragment()", "Lobby Changed -> change!")
    }

    override fun requestQuitToMenu() {
        Log.d("LobbyFragment", "Request to quit!")
        presenter.quitFromLobby(false)
    }

    override fun onRoundChange(lobby: GameLobby) {
        moveToGameView(lobby)
    }

    override fun quitToMenu() {
        view?.let{
            findNavController().navigate(R.id.action_lobbyFragment_to_mainMenuFragment)
        }
    }
}