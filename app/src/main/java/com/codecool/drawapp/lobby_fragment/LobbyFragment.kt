package com.codecool.drawapp.lobby_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codecool.drawapp.R
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
        presenter.createGame()
    }

    override fun onFail() {
        // Failed to create a game
        findNavController().navigate(R.id.action_lobbyFragment_to_mainMenuFragment)
    }

    override fun onSuccess() {
        // Success game create
        loading_bar.visibility = View.INVISIBLE
    }
}