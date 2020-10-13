package com.codecool.drawapp.dependency.upload_image

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.User
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueries
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueryService
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

class UploadServiceImplementation : UploadService, KoinComponent {

    val basicDatabaseQueriesService : BasicDatabaseQueryService by inject()

    override fun uploadImage(bitmap: Bitmap, context : Context,gameLobby: GameLobby) {
        basicDatabaseQueriesService.getMyUserFromDatabase(object : BasicDatabaseQueries.getMyUserFromDatabaseCallback{

            override fun onFail() {
                Log.d("UploadService", "Upload failed!")
            }

            override fun onSuccess(user: User) {
                val fileName = "${gameLobby.gameId}/${gameLobby.round}/${user.userName}"
                val file = createFileFromBitmap(bitmap,context)
                val reference = ProjectDatabase.FIREBASE_STORAGE.getReference(fileName)
                reference.putFile(Uri.fromFile(file))
                    .addOnSuccessListener {
                        Log.d("UploadService", "Image upload success!")
                    }
            }
        })
    }

    private fun createFileFromBitmap(bitmap: Bitmap, context: Context) : File{
        val file : File = File(context.cacheDir,UUID.randomUUID().toString())
        file.createNewFile()

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,0, bos)
        val bitmapData = bos.toByteArray()

        val fileOs = FileOutputStream(file)
        fileOs.write(bitmapData)
        fileOs.flush()
        fileOs.close()
        return file
    }

}