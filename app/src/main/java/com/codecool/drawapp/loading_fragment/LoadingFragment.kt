package com.codecool.drawapp.loading_fragment

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.codecool.drawapp.R
import com.jaredrummler.android.widget.AnimatedSvgView
import kotlinx.android.synthetic.main.fragment_loading.view.*

class LoadingFragment : Fragment(), LoadingContractor {

    lateinit var presenter: LoadingPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LoadingPresenter(this)
        presenter.checkLoggedInStatus()
    }

    override fun isLoggedIn(loggedIn: Boolean) {
        if ( loggedIn ){

        } else findNavController().navigate(R.id.action_loadingFragment_to_loginFragment)
    }
}