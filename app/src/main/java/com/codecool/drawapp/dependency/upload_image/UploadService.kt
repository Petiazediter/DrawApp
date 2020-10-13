package com.codecool.drawapp.dependency.upload_image

import android.content.Context
import android.graphics.Bitmap
import com.codecool.drawapp.data_layer.GameLobby

interface UploadService  {
    fun uploadImage(bitmap : Bitmap, context : Context,gameLobby: GameLobby)
}