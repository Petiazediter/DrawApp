package com.codecool.drawapp.game_view.fragments.guess_section

import com.google.firebase.storage.StorageReference

interface GuessingContractor {
    fun returnFilteredList(list : List<StorageReference>)
}