package com.codecool.drawapp.game_view.fragments.wait_section

import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference

interface WaitingContractor {
    fun allFilesLoaded(files : List<StorageReference>)
}