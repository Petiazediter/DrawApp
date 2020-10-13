package com.codecool.drawapp.dependency.upload_image

import android.content.Context
import android.graphics.Bitmap

interface UploadService  {
    fun uploadImage(bitmap : Bitmap, context : Context)
}