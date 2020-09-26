package com.codecool.drawapp.friends_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.User

class FriendsAdapter(val listOfUser : List<User>, val layoutInflater: LayoutInflater,val view : FriendsContractor) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return FriendViewHolder(layoutInflater.inflate(R.layout.friend_row,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfUser.size
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {}
}