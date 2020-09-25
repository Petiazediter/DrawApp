package com.codecool.drawapp.friend_invites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.ProjectDatabase
import com.codecool.drawapp.data_layer.User
import kotlinx.android.synthetic.main.fragment_invites.*


class InvitesFragment : Fragment(), InvitesContractor {
    lateinit var presenter: InvitesPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_invites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = InvitesPresenter(this)
        presenter.getFriendRequests()
        add_friend_btn.setOnClickListener {
            val name = add_friend_edit.text.toString()
            if ( name != ProjectDatabase.FIREBASE_AUTH.currentUser?.displayName){
                presenter.addFriend(name)
            } else Log.d("InvitesFragment()", "onFail() ")
        }
    }

    override fun emptyInvitesRecycler() {
        loading_bar.visibility = View.INVISIBLE
        empty_alert.visibility = View.VISIBLE
    }

    override fun setInvitesRecycler(list: List<User>) {
        loading_bar.visibility = View.INVISIBLE
        my_invites_recycler.visibility = View.VISIBLE
        my_invites_recycler.adapter = FriendRequestAdapter(list, LayoutInflater.from(requireContext()))
        my_invites_recycler.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }
}