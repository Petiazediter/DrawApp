package com.codecool.drawapp.data_layer

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class ProjectDatabase {
    companion object {
        val FIREBASE_DB = FirebaseDatabase.getInstance()
        val FIREBASE_AUTH = FirebaseAuth.getInstance()
        val FIREBASE_STORAGE = FirebaseStorage.getInstance()
    }
}