package com.codecool.drawapp.friend_invites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.User
import kotlinx.android.synthetic.main.friend_request_row.view.*

class FriendRequestAdapter(private val users : List<User>, private val layoutInflater: LayoutInflater, val view : InvitesContractor) : RecyclerView.Adapter<FriendRequestAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.friend_request_row,parent,false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.name_space.text = users.get(position).userName
        holder.itemView.decline_btn.setOnClickListener{ view.declineFriendRequest(users.get(position).userId) }
    }
}