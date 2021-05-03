package com.codecool.drawapp.game_view.fragments.guess_section

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.codecool.drawapp.R
import com.codecool.drawapp.data_layer.GameLobby
import com.codecool.drawapp.game_view.GameView
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_guessing.*
import org.koin.core.KoinComponent

class GuessingFragment(var files : List<StorageReference>, var gameLobby: GameLobby,val gameView : GuessingFragmentCallback) : Fragment(), GuessingContractor {

    interface GuessingFragmentCallback{
        fun votedToEveryBody()
    }
    data class WordToGuess(val username : String, val word : String)

    lateinit var filteredList : List<WordToGuess>
    lateinit var filteredListRaw : List<StorageReference>
    lateinit var presenter: GuessingPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        presenter = GuessingPresenter(this)
        return inflater.inflate(R.layout.fragment_guessing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.filterWords(files)
    }

    override fun returnFilteredList(list: List<StorageReference>) {
        val names = list.map{ storageReference ->
            val l = storageReference.name.split("---")
            Log.d("GuessingFragment", "username : ${l[0]} | word : ${l[1]}")
            WordToGuess(l[0],l[1])}

        filteredList = names
        filteredListRaw = list
        reloadView()
    }

    private fun reloadView(){
        val wordToGuess = filteredList[0]
        val storageReference = filteredListRaw[0]
        view?.let{
            username.text = wordToGuess.username
            presenter.setDraw(storageReference)
        }
    }

    override fun loadImage(uri: Uri) {
        view?.let{
            Picasso.get().load(uri).into(drawing_iv)
            ok_button.setOnClickListener {
                if ( guess_et.text.toString() == filteredList[0].word) {
                    Toasty.error(requireContext(), "Oops! That's the original word! Choose something else to bait your friends!",Toasty.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                presenter.voteWord(gameLobby = gameLobby, guessWord = guess_et.text.toString(),originalWord = filteredList[0].word)
                filteredList = filteredList.drop(1)
                filteredListRaw = filteredListRaw.drop(1)
                if ( filteredList.size > 0){
                    reloadView()
                } else {
                    gameView.votedToEveryBody()
                }
            }
        }
    }


}