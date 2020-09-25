package com.codecool.drawapp.friend_invites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.ProjectDatabase
import kotlinx.android.synthetic.main.fragment_invites.*


class InvitesFragment : Fragment(), InvitesContractor {
    lateinit var presenter: InvitesPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_invites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = InvitesPresenter(this)

        add_friend_btn.setOnClickListener {
            val name = add_friend_edit.text.toString()
            if ( name != ProjectDatabase.FIREBASE_AUTH.currentUser?.displayName){
                presenter.addFriend(name)
            } else Log.d("InvitesFragment()", "onFail() ")
        }
    }


}