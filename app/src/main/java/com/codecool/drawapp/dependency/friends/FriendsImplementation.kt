package com.codecool.drawapp.dependency.friends

import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.User
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueries
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueryService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.koin.core.KoinComponent
import org.koin.core.inject

class FriendsImplementation : FriendsService, KoinComponent {

    private val basicDatabaseQueryService : BasicDatabaseQueryService by inject()

    interface FriendsCallback{
        fun getFriendsCallback( friends : List<User> )
    }

    override fun getUserFriendList(view: FriendsCallback) {
        val user = ProjectDatabase.FIREBASE_AUTH.currentUser

        basicDatabaseQueryService.getMyUserFromDatabase(object : BasicDatabaseQueries.getMyUserFromDatabaseCallback{
            override fun onFail() {
                view.getFriendsCallback(listOf())
            }

            override fun onSuccess(user: User) {
                val friends = ProjectDatabase.FIREBASE_DB.getReference("friends").child(user.userId)

                friends.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(error: DatabaseError) = view.getFriendsCallback(listOf())

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if ( snapshot.exists()) view.getFriendsCallback(snapshot.children.map{ it.getValue(User::class.java)!!}.toMutableList())
                        else view.getFriendsCallback(listOf())
                    }
                })
            }
        })
    }

    interface AddUserToFriendListCallback{
        fun onComplete()
    }
    override fun addUserToFriendList(userId: String, view : AddUserToFriendListCallback) {

    }
}