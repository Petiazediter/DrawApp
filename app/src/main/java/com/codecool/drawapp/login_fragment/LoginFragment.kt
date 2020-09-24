package com.codecool.drawapp.login_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codecool.drawapp.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment(), LoginContractor {

    lateinit var presenter: LoginPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LoginPresenter(this)
        sign_up_button.setOnClickListener {findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }
        sign_in_button.setOnClickListener {
            hideEdit()
            val userName = username.text.toString()
            val password = username.password.toString()
            presenter.onLogin(userName, password)
        }
    }

    private fun hideEdit(){
        edit_layout.visibility = View.INVISIBLE
        loading_bar.visibility = View.VISIBLE
    }
}