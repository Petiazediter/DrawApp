package com.codecool.drawapp.dependency.register

import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RegisterImplementation : RegisterService {

    interface RegisterCallback{
        fun onError(errorMessage : String)
        fun onSuccess()
    }

    override fun onRegister(username: String, password: String, email: String, view : RegisterCallback) {
        val user_table = ProjectDatabase.FIREBASE_DB.getReference("users")
        user_table.addListenerForSingleValueEvent( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                return view.onError("Database access forbidden!")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if ( snapshot.exists()){
                    if (snapshot.children.filter { it.getValue(User::class.java)!!.userName == username || it.getValue(User::class.java)!!.emailAdress == email }.isNotEmpty()){
                        return view.onError("Username or Email is already taken!")
                    }
                }
                registerAccount(username,password,email,view)
            }
        })
    }

    private fun registerAccount(username: String, password: String, email: String, view: RegisterImplementation.RegisterCallback) {
        ProjectDatabase.FIREBASE_AUTH.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            if ( it.isSuccessful()){
                val users = ProjectDatabase.FIREBASE_DB.getReference("users")
                val newId = users.push().key.toString()
                val user : User = User(newId, username, email, true)
                users.child(newId).setValue(user)
                it.result?.user?.let{
                     val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(username).build();
                     it.updateProfile(profileUpdates)
                }
                view.onSuccess()
            }else {
                it.exception?.message?.let{ view.onError(it) }
            }
        }
    }
}