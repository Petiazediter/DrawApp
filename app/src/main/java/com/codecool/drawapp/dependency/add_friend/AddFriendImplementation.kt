package com.codecool.drawapp.dependency.add_friend

import com.codecool.drawapp.data_layer.FriendInvite
import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.User
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueries
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueryService
import com.codecool.drawapp.dependency.friends.FriendsImplementation
import com.codecool.drawapp.dependency.friends.FriendsService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.koin.core.KoinComponent
import org.koin.core.inject

class AddFriendImplementation : AddFriendService, KoinComponent {
    private val friendService : FriendsService by inject()
    private val basicDatabaseService : BasicDatabaseQueryService by inject()

    interface AddFriendCallback{
        fun onFail()
        fun onSuccess()
    }

    override fun addFriend(name: String, view: AddFriendCallback) {
        basicDatabaseService.getMyUserFromDatabase(object : BasicDatabaseQueries.getMyUserFromDatabaseCallback{
            override fun onFail() {
                view.onFail()
            }

            override fun onSuccess(myUser: User) {
                // 1.step : We need to know if there is a user with the specific username ( name : String )!
                ProjectDatabase.FIREBASE_DB.getReference("users").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        // There's no Firebase connection, the friend request failed.
                        view.onFail()
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        // If there's no such table ( which is weird btw ) the friend invite fail.
                        if ( !snapshot.exists()) view.onFail()
                        else{
                            // We need to get the target user by the name ( name : String )
                            val filterList = snapshot.children.filter{ it.getValue(User::class.java)!!.userName == name }
                            // If there's no such user the request fail
                            if ( filterList.isNullOrEmpty()) view.onFail()
                            else {
                                // If there's a user then we need his Id
                                val userId = filterList[0].getValue(User::class.java)!!.userId
                                // We can move forward to 2nd step!
                                attemptFriendRequest(myUser,userId,view)
                            }
                        }
                    }
                })
            }
        })
    }

    private fun attemptFriendRequest(myUser: User, userId: String, view: AddFriendImplementation.AddFriendCallback) {
        // We have our user and the target user's Id.
        // Now we had to check out if we already added him to our list or reversed
        ProjectDatabase.FIREBASE_DB.getReference("friendRequests").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                // If there's no connection request fails.
                view.onFail()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                // If there's no invites at all the invite successful, we can move forward to 3rd step.
                if ( !snapshot.exists()) checkForFriends(myUser,userId ,view)
                else {
                    // We must search for friend invite between target and current user.
                    if  (snapshot.children.filter{
                        val invite = it.getValue(FriendInvite::class.java)!!
                         // If the target sent us an invte                        or        We sent an invite to the target
                        (invite.fromId == userId || invite.toId == myUser.userId) || (invite.toId == userId && invite.fromId == myUser.userId)
                    }.isNullOrEmpty()){
                        // If there's no request between us we can move forward to 3rd step.
                        checkForFriends(myUser,userId ,view)
                        // Else request fails.
                    } else view.onFail()
                }
            }
        })
    }

    private fun checkForFriends(myUser: User, userId: String, view: AddFriendImplementation.AddFriendCallback) {
        // We must get our User's friend list. If the target user in it the request must fail.
        friendService.getUserFriendList(object : FriendsImplementation.FriendsCallback {
            override fun getFriendsCallback(friends: List<User>) {
                // If the user don't have friends we can send the request!
                if ( friends.isNullOrEmpty()) sendFriendRequest(myUser,userId,view)
                else {
                    // otherwise we should find the target in the friend list
                    if ( friends.filter { it.userId == userId }.isNullOrEmpty()){
                        // If there's no user with the target's id we can send the request
                        sendFriendRequest(myUser,userId,view)
                        // Else request must fall because they're already friends.
                    } else view.onFail()
                }
            }
        })
    }

    private fun sendFriendRequest(myUser: User, userId: String, view: AddFriendImplementation.AddFriendCallback) {
        val requests = ProjectDatabase.FIREBASE_DB.getReference("friendRequests")
        val requestId = requests.push().key.toString()

        // We can Insert the new friend request now ! 🎉🎉🎉
        requests.child(requestId).setValue(FriendInvite(myUser.userId, userId)).addOnCompleteListener {
            if ( it.isSuccessful()){
                view.onSuccess()
            } else view.onFail()
        }
    }
}