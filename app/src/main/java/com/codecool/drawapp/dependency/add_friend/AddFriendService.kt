package com.codecool.drawapp.dependency.add_friend

interface AddFriendService {
    fun addFriend(name : String, view : AddFriendImplementation.AddFriendCallback)
}