package com.codecool.drawapp.friend_invites

import android.util.Log
import com.codecool.drawapp.dependency.add_friend.AddFriendImplementation
import com.codecool.drawapp.dependency.add_friend.AddFriendService
import org.koin.core.KoinComponent
import org.koin.core.inject

class InvitesPresenter ( view : InvitesContractor) : KoinComponent {
    val addFriendService : AddFriendService by inject()

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
}