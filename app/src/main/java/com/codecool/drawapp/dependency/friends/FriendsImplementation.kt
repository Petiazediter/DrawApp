package com.codecool.drawapp.dependency.friends

import com.codecool.drawapp.data_layer.ProjectDatabase

class FriendsImplementation : FriendsService {
    interface FriendsCallback{

    }

    override fun getUserFriendList(view: FriendsCallback) {
        val user = ProjectDatabase.FIREBASE_AUTH.currentUser
        user?.let{

        }
    }
}