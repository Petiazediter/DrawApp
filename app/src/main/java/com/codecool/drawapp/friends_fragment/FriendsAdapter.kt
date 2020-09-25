package com.codecool.drawapp.friends_fragment

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codecool.drawapp.data_layer.User

class FriendsAdapter(val listOfUser : List<User>) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return listOfUser.size
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}