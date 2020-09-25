package com.codecool.drawapp.dependency.add_friend

import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.User
import com.codecool.drawapp.dependency.friends.FriendsImplementation
import com.codecool.drawapp.dependency.friends.FriendsService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.koin.core.KoinComponent
import org.koin.core.inject

class AddFriendImplementation : AddFriendService, KoinComponent {
    val friendService : FriendsService by inject()

    interface AddFriendCallback{
        fun onFail()
        fun onSuccess()
    }

    override fun addFriend(name: String, view: AddFriendCallback) {
        val users = ProjectDatabase.FIREBASE_DB.getReference("users")
        users.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                view.onFail()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if ( !snapshot.exists()) view.onFail()
                else {
                    val filterList = snapshot.children.filter { it.getValue(User::class.java)!!.userName == name }
                    if (filterList.isNullOrEmpty()) return view.onFail()
                    val user : User = filterList[0].getValue(User::class.java)!!
                    friendService.getUserFriendList(object : FriendsImplementation.FriendsCallback{
                        override fun getFriendsCallback(friends: List<User>) {
                            if ( user in friends) {
                                view.onFail()
                            } else {
                                attemptAddFriend(user,view)
                            }
                        }
                    })
                }
            }
        })
    }

    private fun attemptAddFriend(user : User,view: AddFriendCallback){
        ProjectDatabase.FIREBASE_AUTH.currentUser?.let{
            val myId = it.uid
            val friend_invites = ProjectDatabase.FIREBASE_DB.getReference("friendInvites").child(myId)
            friend_invites.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    view.onFail()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if ( !snapshot.exists()) addToList(user, view)
                    else {
                        if ( snapshot.children.filter { it.key == user.userId }.isEmpty()) addToList(user,view)
                        else view.onFail() // Már egyszer felvetted de még nem fogadta el
                    }
                }
            })
            return
        }
        view.onFail()
    }

    private fun addToList(user : User, view : AddFriendCallback){
        ProjectDatabase.FIREBASE_AUTH.currentUser?.let {
            val myId = it.uid
            ProjectDatabase.FIREBASE_DB.getReference("friendInvites").child(myId).child(user.userId).setValue(user)
            view.onSuccess()
        }
    }
}