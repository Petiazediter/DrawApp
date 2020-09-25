package com.codecool.drawapp.friends_fragment

interface FriendsContractor {
    fun displayRecylcer(adapter: FriendsAdapter)
    fun emptyRecycler()
}