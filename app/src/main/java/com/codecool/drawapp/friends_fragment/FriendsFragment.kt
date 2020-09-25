package com.codecool.drawapp.friends_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codecool.drawapp.R
import kotlinx.android.synthetic.main.fragment_friends.*

class FriendsFragment : Fragment(), FriendsContractor {

    lateinit var presenter : FriendsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = FriendsPresenter(this)
        presenter.setUpRecycler()

        my_invites.setOnClickListener {
            
        }
    }

    override fun displayRecycler(adapter: FriendsAdapter) {
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL, false)
        recycler.visibility = View.VISIBLE
        loading_bar.visibility = View.INVISIBLE
    }

    override fun emptyRecycler() {
        loading_bar.visibility = View.INVISIBLE
        empty_alert.visibility = View.VISIBLE
    }
}