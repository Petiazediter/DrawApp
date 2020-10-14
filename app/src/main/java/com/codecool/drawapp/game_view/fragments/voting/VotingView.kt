package com.codecool.drawapp.game_view.fragments.voting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.game_view.GameView
import com.codecool.drawapp.game_view.GameViewInterface
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_voting.*
import kotlinx.android.synthetic.main.loading_view.*
import kotlinx.android.synthetic.main.loading_view.view.*

interface VotingViewInterface{

}

class VotingView(val gameLobby: GameLobby ,val gameView : VotingViewInterface) : VotingContractor, Fragment(), GameViewInterface {

    lateinit var presenter: VotingPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        linear_layout_vote.visibility = View.INVISIBLE
        view.loading_bar.visibility = View.VISIBLE
        view.loading_bar.startRippleAnimation()
        presenter = VotingPresenter(this)

        super.onViewCreated(view, savedInstanceState)
        context?.let{Toasty.info(it, "Let's wait for the others! :)", Toasty.LENGTH_SHORT).show()}
        presenter.startLoading(gameLobby)
    }

    override fun onLobbyChange(gameLobby: GameLobby) {
        presenter.gameLobby = gameLobby
    }

    override fun everyBodyGuessed() {

        presenter.loadGuessings()
        /*
            loading_bar.visibility = View.GONE
            linear_layout_vote.visibility = View.VISIBLE
        */
    }
}