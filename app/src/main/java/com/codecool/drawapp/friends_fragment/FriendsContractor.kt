package com.codecool.drawapp.friends_fragment

interface FriendsContractor {
    fun displayRecycler(adapter: FriendsAdapter)
    fun emptyRecycler()
}