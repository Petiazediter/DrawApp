package com.codecool.drawapp.game_view.fragments.voting

import com.codecool.drawapp.data_layer.Vote
import com.codecool.drawapp.data_layer.VoteWrapper

interface VotingContractor {
    fun everyBodyGuessed()
    fun setUpAdapter(votes : List<VoteWrapper>)
}