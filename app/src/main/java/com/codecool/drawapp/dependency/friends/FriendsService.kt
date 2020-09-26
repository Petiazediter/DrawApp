package com.codecool.drawapp.dependency.friends

interface FriendsService {
    fun getUserFriendList(view : FriendsImplementation.FriendsCallback)
    fun addUserToFriendList ( userId : String, view : FriendsImplementation.AddUserToFriendListCallback)
}