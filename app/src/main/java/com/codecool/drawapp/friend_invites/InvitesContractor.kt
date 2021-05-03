package com.codecool.drawapp.friend_invites

import com.codecool.drawapp.data_layer.User

interface InvitesContractor {
    fun setInvitesRecycler(list : List<User>)
    fun emptyInvitesRecycler()
    fun acceptFriendRequest(userId : String)
    fun declineFriendRequest(userId : String)
}