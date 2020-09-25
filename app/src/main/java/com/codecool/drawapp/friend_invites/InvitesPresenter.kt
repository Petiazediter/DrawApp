package com.codecool.drawapp.friend_invites

import android.util.Log
import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.User
import com.codecool.drawapp.dependency.add_friend.AddFriendImplementation
import com.codecool.drawapp.dependency.add_friend.AddFriendService
import com.codecool.drawapp.dependency.firend_invites.FriendInvitesImplementation
import com.codecool.drawapp.dependency.firend_invites.FriendInvitesService
import org.koin.core.KoinComponent
import org.koin.core.inject

class InvitesPresenter ( val view : InvitesContractor) : KoinComponent {
    private val addFriendService : AddFriendService by inject()
    private val friendInvitesService : FriendInvitesService by inject()

    fun addFriend(name : String){
        addFriendService.addFriend(name, object : AddFriendImplementation.AddFriendCallback{
            override fun onFail() {
                Log.d("InvitesPresenter", "addFriend() -> fail")
            }

            override fun onSuccess() {
                Log.d("InvitesPresenter", "addFriend() -> success")
            }
        })
    }

    fun getFriendRequests(){
        friendInvitesService.getUserFriendInvites(object : FriendInvitesImplementation.FriendInvitesCallback{
            override fun getUserInvites(invites: List<User>) {
                Log.d("InvitesPresenter", "getFriendRequests() -> " + invites.size.toString() + " requests.")
                if ( invites.isNullOrEmpty()) view.emptyInvitesRecycler()
                else view.setInvitesRecycler(invites)
            }
        })
    }

    fun acceptFriendRequest ( userId : String){

    }

    fun declineFriendRequest(userId : String){

    }
}