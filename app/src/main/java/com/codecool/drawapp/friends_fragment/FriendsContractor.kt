package com.codecool.drawapp.friends_fragment

import com.codecool.drawapp.data_layer.User

interface FriendsContractor {
    fun displayRecycler(friends: List<User>)
    fun emptyRecycler()
}