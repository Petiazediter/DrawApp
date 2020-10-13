package com.codecool.drawapp.game_view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codecool.drawapp.MainActivity
import com.codecool.drawapp.R
import com.codecool.drawapp.api.ApiSingleton
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.dependency.lobby.lobby_listener.LobbyListener
import com.codecool.drawapp.game_view.fragments.draw_section.DrawFragment
import com.muddzdev.quickshot.QuickShot
import kotlinx.android.synthetic.main.fragment_draw.*
import kotlinx.android.synthetic.main.fragment_draw.view.*
import kotlinx.coroutines.*

class GameView : Fragment(), GameContractor, LobbyListener, MainActivity.BackButtonInterface {

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
                presenter.attachToListener(this,gameLobby)
                buildRound()
            } ?: run {
                Log.d( "GameView", "onViewCreated() -> No gameId argument!")
                backToMenu()}
        } ?: run {
            Log.d( "GameView", "onViewCreated() -> No arguments!")
            backToMenu()}
    }

    override fun backToMenu() {
        Log.d("GameView", "backToMenu()")
        findNavController().navigate(R.id.action_gameView_to_mainMenuFragment)
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
                withContext(Dispatchers.Main) {
                    view?.let {
                        it.timer_text.text = (60 - num).toString()
                    }
                }
            }

            withContext(Dispatchers.Main){
                view?.let{
                    saveDrawAsImage()
                }
            }

        }
    }

    private fun saveDrawAsImage(){
        val draw = draw_container
        val b : Bitmap = Bitmap.createBitmap(draw.layoutParams.width,draw.layoutParams.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(b)
        draw.layout(draw.left,draw.top,draw.right,draw.bottom)
        draw.draw(canvas)

        Log.d("GameView()", "Bitmap created!")

    }

    override fun onLobbyChange(lobby: GameLobby) {
        Log.d("GameView", "onLobbyChange() -> LobbyChanged")
    }

    override fun onRoundChange(lobby: GameLobby) {
        // To Do
    }

    override fun requestQuitToMenu() {
        presenter.unAttach()
        presenter.onQuit(true)
        Log.d("GameView", "requestQuitToMenu()")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unAttach()
    }

    override fun onBackButtonPressed() {
        presenter.onQuit(false)
        Log.d("GameView", "onBackButtonPressed()")
    }
}