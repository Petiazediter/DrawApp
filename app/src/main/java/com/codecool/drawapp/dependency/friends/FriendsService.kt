package com.codecool.drawapp.dependency.friends

interface FriendsService {
    fun getUserFriendList(view : FriendsImplementation.FriendsCallback)
}