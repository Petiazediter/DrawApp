package com.codecool.drawapp.game_view.fragments.guess_section

import android.net.Uri
import com.google.firebase.storage.StorageReference

interface GuessingContractor {
    fun returnFilteredList(list : List<StorageReference>)
    fun loadImage(link : Uri)
}