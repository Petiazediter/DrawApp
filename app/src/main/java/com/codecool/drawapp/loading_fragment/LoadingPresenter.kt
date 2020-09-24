package com.codecool.drawapp.loading_fragment

import com.codecool.drawapp.data_layer.ProjectDatabase

class LoadingPresenter (val view : LoadingContractor) {

    fun checkLoggedInStatus(){
        ProjectDatabase.FIREBASE_AUTH.currentUser?.let{
            view.isLoggedIn(true)
            return
        }
        view.isLoggedIn(false)
    }

}