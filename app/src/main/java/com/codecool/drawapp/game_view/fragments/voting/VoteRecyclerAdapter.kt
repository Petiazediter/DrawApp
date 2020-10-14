  package com.codecool.drawapp.game_view.fragments.voting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.Vote
import com.codecool.drawapp.data_layer.VoteWrapper
import kotlinx.android.synthetic.main.guess_row.view.*

interface VoteRecyclerInterface{
    fun voteForWord ( word : String )
}

class VoteRecyclerAdapter(val voteWrapper: VoteWrapper, val layoutInflater: LayoutInflater, val view :  VoteRecyclerInterface) :
    RecyclerView.Adapter<VoteRecyclerAdapter.VoteViewHolder>() {

    override fun getItemCount(): Int {
        return voteWrapper.guesses.size
    }

    override fun onBindViewHolder(holder: VoteViewHolder, position: Int) {
        holder.itemView.guess_text.text = voteWrapper.guesses[position].guessedWord
        holder.itemView.setOnClickListener {
            view.voteForWord(word = voteWrapper.guesses[position].guessedWord)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoteViewHolder {
        val layout = layoutInflater.inflate(R.layout.guess_row, parent,false)
        return VoteViewHolder(layout)
    }

    class VoteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
}