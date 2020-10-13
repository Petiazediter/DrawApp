package com.codecool.drawapp.dependency.upload_image

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.codecool.drawapp.data_layer.ProjectDatabase
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

class UploadServiceImplementation : UploadService {

    interface UploadImageCallback{

    }

    override fun uploadImage(bitmap: Bitmap, context : Context,view : UploadImageCallback) {
        val file = createFileFromBitmap(bitmap,context)
        val reference = ProjectDatabase.FIREBASE_STORAGE.getReference("test")
        reference.putFile(Uri.fromFile(file))
            .addOnSuccessListener {
                Log.d("UploadService", "Image upload success!")
            }
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