package com.codecool.drawapp.game_view.fragments.voting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.data_layer.VoteWrapper
import com.codecool.drawapp.game_view.GameViewInterface
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_guessing.*
import kotlinx.android.synthetic.main.fragment_voting.*
import kotlinx.android.synthetic.main.fragment_voting.drawing_iv
import kotlinx.android.synthetic.main.loading_view.*
import kotlinx.android.synthetic.main.loading_view.view.*

interface VotingViewInterface{

}

class VotingView(val gameLobby: GameLobby ,val gameView : VotingViewInterface, val files : List<StorageReference>) : VotingContractor, Fragment(), GameViewInterface, VoteRecyclerInterface {

    lateinit var presenter: VotingPresenter
    lateinit var votes: List<VoteWrapper>

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
    }

    override fun setUpAdapter(votes: List<VoteWrapper>) {
        this.votes = votes

        loading_bar.visibility = View.GONE
        linear_layout_vote.visibility = View.VISIBLE
        loading_bar.stopRippleAnimation()

        val adapter = VoteRecyclerAdapter(votes[0], LayoutInflater.from(requireContext()),this)
        recycler_list.adapter = adapter
        recycler_list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        files[0].downloadUrl.addOnSuccessListener {
            Picasso.get().load(it.toString()).into(drawing_iv)
        }
    }

    override fun voteForWord(word: String) {
        presenter.voteForWord(word,votes[0].originalWord)
    }

}