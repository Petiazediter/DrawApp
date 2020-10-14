package com.codecool.drawapp.game_view

import android.app.ActionBar
import android.graphics.Bitmap
import android.graphics.Canvas
import android.icu.util.Measure
import android.media.MediaMetadataRetriever
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
import com.codecool.drawapp.dependency.upload_image.UploadService
import com.codecool.drawapp.dependency.upload_image.UploadServiceImplementation
import com.codecool.drawapp.game_view.fragments.draw_section.DrawFragment
import com.codecool.drawapp.game_view.fragments.guess_section.GuessingFragment
import com.codecool.drawapp.game_view.fragments.voting.VotingView
import com.codecool.drawapp.game_view.fragments.voting.VotingViewInterface
import com.codecool.drawapp.game_view.fragments.wait_section.WaitingForOthersFragment
import com.google.firebase.storage.StorageReference
import com.muddzdev.quickshot.QuickShot
import kotlinx.android.synthetic.main.fragment_draw.*
import kotlinx.android.synthetic.main.fragment_draw.view.*
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject


interface GameViewInterface{
    fun onLobbyChange(gameLobby: GameLobby)
}

class GameView : Fragment(), GameContractor,KoinComponent, LobbyListener, MainActivity.BackButtonInterface, GuessingFragment.GuessingFragmentCallback, VotingViewInterface {

    private enum class GameState(val state : Int){
        DRAWING(1),
        WAITING(2),
        GUESSING(3),
        REVEALING(4),
        SCORES(5)
    }

    private var files: List<StorageReference>? = null
    private var currentState = GameState.DRAWING.state
    val uploadService : UploadService by inject()
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
                openFragment(GameState.DRAWING.state)
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


    override fun getWord(word: String) {
        drawFragment.setWord(word)
        startTimer()
    }

    private fun startTimer() {
        GlobalScope.launch {
            (0..10).forEach {num ->
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
                    openFragment(GameState.WAITING.state)
                }
            }

        }
    }

    private fun saveDrawAsImage(){
        val draw = drawFragment.getCanvasView()
        draw?.let{draw ->
                val bitmap = Bitmap.createBitmap(draw.width, draw.height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                draw.layout(0,0,draw.width,draw.height)
                draw.draw(canvas)
                Log.d("GameView", "Bitmap created!")
                uploadService.uploadImage(bitmap,requireContext(),presenter.gameLobby!!, drawFragment.view?.word_tv?.text.toString())
                presenter.createListToUsersWord(drawFragment.view?.word_tv?.text.toString())
        } ?: run{ Log.d("GameView", "Draw is null!")}
    }

    override fun onRoundChange(lobby: GameLobby) {

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

    private fun openFragment(state : Int){
        currentState = state
        when ( state ){
            // Drawing state
            GameState.DRAWING.state -> {
                drawFragment = DrawFragment()
                loadFragment(drawFragment)
                presenter.getRandomWord()
            }
            // Waiting state
            GameState.WAITING.state -> {
                val waitingFragment = WaitingForOthersFragment(presenter.gameLobby!!,this)
                loadFragment(waitingFragment)
            }

            GameState.GUESSING.state -> {
                files?.let{
                    val guessingFragment = GuessingFragment(it, presenter.gameLobby!!,this)
                    Log.d("GameView", "loadGuessingState -> :)")
                    loadFragment(guessingFragment)
                } ?: run{
                    Log.d("GameView", "onAllFilesLoaded() -> :(")}
            }

            GameState.REVEALING.state -> {
                loadFragment(VotingView(presenter.gameLobby!!, this))
            }
        }
    }

    private fun loadFragment(fragment : Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onLobbyChange(lobby: GameLobby) {
        Log.d("GameView", "onLobbyChange() -> LobbyChanged")
        val fragment = parentFragmentManager.fragments[0] as? GameViewInterface
        fragment?.onLobbyChange(lobby)
    }

    fun onAllFilesLoaded(files: List<StorageReference>){
        Log.d("GameView", "onAllFilesLoaded() -> :)")
        this.files = files
        openFragment(GameState.GUESSING.state)
    }

    override fun votedToEveryBody() {
        openFragment(GameState.REVEALING.state)
    }
}