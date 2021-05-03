package com.codecool.drawapp.dependency.basic_queries

import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BasicDatabaseQueries : BasicDatabaseQueryService {

    interface getMyUserFromDatabaseCallback{
        fun onSuccess(user : User)
        fun onFail()
    }

    override fun getMyUserFromDatabase (view : getMyUserFromDatabaseCallback){
        ProjectDatabase.FIREBASE_AUTH.currentUser?.let{ firebaseUser ->
            ProjectDatabase.FIREBASE_DB.getReference("users").addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    view.onFail()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if ( !snapshot.exists()) view.onFail()
                    else {
                        val filter = snapshot.children.filter { it.getValue(User::class.java)!!.userName == firebaseUser.displayName }
                        if (filter.isNotEmpty()) view.onSuccess(filter.get(0).getValue(
                            User::class.java)!!)
                        else view.onFail()
                    }
                }
            })
        }
    }

    interface GetUserByIdCallback{
        fun onComplete ( user : User)
        fun onError ( )
    }
    override fun getUserById(userId: String, view : GetUserByIdCallback) {
        ProjectDatabase.FIREBASE_DB.getReference("users").addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                view.onError()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if ( !snapshot.exists()) view.onError()
                else {
                    val filtered = snapshot.children.filter { it.getValue(User::class.java)!!.userId == userId }
                    if (filtered.isNullOrEmpty()) view.onError()
                    else view.onComplete(filtered.map{it.getValue(User::class.java)!!}[0])
                }
            }
        })

    }
}