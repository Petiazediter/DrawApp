package com.codecool.drawapp.dependency.friends

import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FriendsImplementation : FriendsService {
    interface FriendsCallback{
        fun getFriendsCallback( firends : List<User> )
    }

    override fun getUserFriendList(view: FriendsCallback) {
        val user = ProjectDatabase.FIREBASE_AUTH.currentUser
        user?.let{ firebaseUser ->
            val friends = ProjectDatabase.FIREBASE_DB.getReference("friends").child(firebaseUser.uid)
            friends.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) = view.getFriendsCallback(listOf())

                override fun onDataChange(snapshot: DataSnapshot) {
                    if ( snapshot.exists()) view.getFriendsCallback(snapshot.children.map{ it.getValue(User::class.java)!!}.toMutableList())
                    else view.getFriendsCallback(listOf())
                }
            })
        }
    }
}