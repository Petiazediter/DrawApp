package com.codecool.drawapp.loading_fragment

import android.util.Log
import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class LoadingPresenter (val view : LoadingContractor) {

    fun checkLoggedInStatus(){
        ProjectDatabase.FIREBASE_AUTH.currentUser?.let{ firebaseUser ->
            Log.d("LoadingPresenter", "Email : ")
            ProjectDatabase.FIREBASE_DB.getReference("users").addListenerForSingleValueEvent( object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    return view.isLoggedIn(false)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if ( snapshot.exists()){
                        view.isLoggedIn(snapshot.children.filter {
                            val user = it.getValue(User::class.java)
                            if ( user == null) false
                            else user.emailAdress == firebaseUser.email
                        }.isEmpty())
                    }
                    view.isLoggedIn(false)
                }
            })
            return
        }
        view.isLoggedIn(false)
    }



}