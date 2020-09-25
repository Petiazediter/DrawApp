package com.codecool.drawapp.dependency.firend_invites

import android.util.Log
import com.codecool.drawapp.data_layer.FriendInvite
import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.User
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueries
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueryService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception
import java.lang.NullPointerException

class FriendInvitesImplementation : FriendInvitesService, KoinComponent {
    private val basicDatabaseQueryService : BasicDatabaseQueryService by inject()

    interface FriendInvitesCallback{
        fun getUserInvites(invites : List<User>)
    }

    override fun getUserFriendInvites(view: FriendInvitesCallback) {
        // 1st step we get our user
        basicDatabaseQueryService.getMyUserFromDatabase(object : BasicDatabaseQueries.getMyUserFromDatabaseCallback{
            override fun onFail() {
                view.getUserInvites(listOf())
            }

            override fun onSuccess(myUser: User) {
                // 2nd step we need to get all the userIds who sent request to us.
                ProjectDatabase.FIREBASE_DB.getReference("friendRequests").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        return view.getUserInvites(listOf())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if ( !snapshot.exists()) view.getUserInvites(listOf())
                        else{
                            val filteredList = snapshot.children.filter{
                                val request = it.getValue(FriendInvite::class.java)!!
                                request.toId == myUser.userId }

                            if (filteredList.isNullOrEmpty()) view.getUserInvites(listOf())
                            else getUsersByIds(filteredList.map{ it -> it.getValue(FriendInvite::class.java)!!.fromId }, view,myUser)
                        }
                    }
                })
            }
        })
    }

    private fun getUsersByIds(filteredList: List<String>, view: FriendInvitesImplementation.FriendInvitesCallback, myUser: User) {
        // We need to get the users behind the Id's
        ProjectDatabase.FIREBASE_DB.getReference("users").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                view.getUserInvites(listOf())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if ( !snapshot.exists()) view.getUserInvites(listOf())
                else {
                    val users = snapshot.children.filter {
                        val user = it.getValue(User::class.java)!!
                        user.userId in filteredList}.map{it.getValue(User::class.java)!!}
                        view.getUserInvites(users)
                }
            }
        })
    }

    interface FriendRequestActionCallback{
        fun onComplete()
    }

    override fun declineFriendInvite(userId: String, view: FriendRequestActionCallback) {
        basicDatabaseQueryService.getMyUserFromDatabase(object : BasicDatabaseQueries.getMyUserFromDatabaseCallback{
            override fun onFail() {
                Log.d("ERROR", "FATAL ERROR")
                throw NullPointerException()
            }

            override fun onSuccess(user: User) {
                ProjectDatabase.FIREBASE_DB.getReference("friendRequests").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        view.onComplete()
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if ( snapshot.exists()){
                            val theInvite = snapshot.children.filter{ val invite = it.getValue(FriendInvite::class.java)!!
                                invite.toId == user.userId && invite.fromId == userId }

                            if  (theInvite.isNotEmpty()){
                                ProjectDatabase.FIREBASE_DB.getReference("friendRequests").child(theInvite[0].key.toString()).removeValue().addOnCompleteListener {
                                    view.onComplete()
                                }
                            } else {
                                view.onComplete()
                            }
                        }
                    }
                })
            }
        })
    }
}