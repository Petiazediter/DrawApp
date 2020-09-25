package com.codecool.drawapp.friend_invites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.User

class FriendRequestAdapter(private val users : List<User>, private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<FriendRequestAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.friend_request_row,parent,false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }
}