package com.codecool.drawapp.game_view

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codecool.drawapp.R
import com.codecool.drawapp.api.ApiSingleton
import com.codecool.drawapp.game_view.fragments.draw_section.DrawFragment
import kotlinx.android.synthetic.main.fragment_draw.*
import kotlinx.android.synthetic.main.fragment_draw.view.*
import kotlinx.coroutines.*

class GameView : Fragment(), GameContractor {

    lateinit var presenter : GamePresenter
    lateinit var drawFragment: DrawFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{
            val gameLobby = it.getString("gameId")
            gameLobby?.let{
                presenter = GamePresenter(this)
                buildRound()
            } ?: run {
                Log.d( "GameView", "onViewCreated() -> No gameId argument!")
                findNavController().navigate(R.id.action_gameView_to_lobbyFragment)}
        } ?: run {
            Log.d( "GameView", "onViewCreated() -> No arguments!")
            findNavController().navigate(R.id.action_gameView_to_lobbyFragment)}
    }

    private fun buildRound(){
        drawFragment = DrawFragment()
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, drawFragment)
        transaction.addToBackStack(null)
        transaction.commit()

        presenter.getRandomWord()
    }

    override fun getWord(word: String) {
        drawFragment.setWord(word)
        startTimer()
    }

    private fun startTimer() {
        GlobalScope.launch {
            (0..60).forEach {num ->
                delay(1000)
                withContext(Dispatchers.Main,{view?.let{
                    it.timer_text.text = (60-num).toString()}
                })
            }

        }
    }

}