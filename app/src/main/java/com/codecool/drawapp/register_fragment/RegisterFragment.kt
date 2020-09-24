package com.codecool.drawapp.register_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codecool.drawapp.R
import com.codecool.drawapp.dependency.register.RegisterService
import kotlinx.android.synthetic.main.fragment_loading.*
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment(), RegisterContractor {

    lateinit var presenter : RegisterPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = RegisterPresenter(this)

        sign_in_button.setOnClickListener { findNavController().navigate(R.id.action_registerFragment_to_loginFragment) }
        sign_up_button.setOnClickListener {
            hideEdit()

            val userName = username.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()
            val password2 = password2.text.toString()
            presenter.attemptRegister(userName,email,password,password2)
        }
    }

    private fun hideEdit(){
        edit_layout.visibility = View.INVISIBLE
        loading_bar.visibility = View.VISIBLE
    }

    private fun showEdit(){
        edit_layout.visibility = View.VISIBLE
        loading_bar.visibility = View.INVISIBLE
    }

    override fun onError(errorMessage: String) {
        showEdit()
        username.setError(errorMessage)
    }

    override fun onSuccess() {
        findNavController().navigate(R.id.action_registerFragment_to_loadingFragment)
    }
}