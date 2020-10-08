package com.codecool.drawapp.join_game_fragment

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codecool.drawapp.R
import kotlinx.android.synthetic.main.fragment_join_game.*

class JoinGameFragment : Fragment(), JoinGameContractor {
    lateinit var presenter: JoinGamePresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_join_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = JoinGamePresenter(this)
        code_edit.filters = arrayOf(InputFilter.AllCaps())
    }
}