package com.codecool.drawapp.dependency.login

import android.util.Log
import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class LoginImplementation : LoginService {
    interface LoginCallback {
        fun onSuccess()
        fun onError(errorMsg : String)
    }

    override fun onLogin(username: String, password: String, view: LoginCallback) {
        ProjectDatabase.FIREBASE_DB.getReference("users").addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    return view.onError(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if ( snapshot.exists()){

                        val filteredList = snapshot.children.filter{ it.getValue(User::class.java)?.let{ it.userName == username } == true }
                        Log.d("LoginService", "onDataChange() -> " + filteredList.toString())
                        if (filteredList.isNotEmpty()) {
                            val user = filteredList[0].getValue(User::class.java)!!
                            attemptLogin(user.emailAdress, password,view)
                            return
                        }
                        Log.d("LoginService", "onDataChange() -> Not successful")
                        view.onError("No user found!")
                    } else {
                        return view.onError("No user found.")
                    }
                }
            })
    }

    private fun attemptLogin(email: String, password: String, view : LoginCallback){
        ProjectDatabase.FIREBASE_AUTH.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if ( it.isSuccessful){
                view.onSuccess()
            } else {
                view.onError("No user found.")
                Log.d("LoginService", "attemptLogin() -> Not successful")
            }
        }
    }
}