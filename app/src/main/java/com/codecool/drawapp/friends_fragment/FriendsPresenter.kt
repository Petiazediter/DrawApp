package com.codecool.drawapp.friends_fragment

import com.codecool.drawapp.data_layer.User
import com.codecool.drawapp.dependency.friends.FriendsImplementation
import com.codecool.drawapp.dependency.friends.FriendsService
import org.koin.core.KoinComponent
import org.koin.core.inject

class FriendsPresenter(val view : FriendsContractor) : KoinComponent {
    val friendsService : FriendsService by inject()
    fun setUpRecycler(){
        friendsService.getUserFriendList(object : FriendsImplementation.FriendsCallback{
            override fun getFriendsCallback(friends: List<User>) {
                if ( friends.isNullOrEmpty()) view.emptyRecycler()
                else view.displayRecycler(friends)
            }
        })
    }


}