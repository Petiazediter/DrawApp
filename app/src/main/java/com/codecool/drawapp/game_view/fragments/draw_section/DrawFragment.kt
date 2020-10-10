package com.codecool.drawapp.game_view.fragments.draw_section

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codecool.drawapp.R
import kotlinx.android.synthetic.main.fragment_draw.*
import kotlinx.android.synthetic.main.fragment_draw.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class DrawFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_draw, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val canvasView = CanvasView(requireContext())
        view.draw_container.addView(canvasView)
        Log.d("DrawFragment()", "Canvas view added to frame layout!")
        erase_button.setOnClickListener { canvasView.setColor(Color.WHITE) }
        color_black.setOnClickListener { canvasView.setColor(Color.BLACK) }
        color_blue.setOnClickListener  { canvasView.setColor(Color.BLUE) }
        color_red.setOnClickListener   { canvasView.setColor(Color.RED) }
        color_yellow.setOnClickListener { canvasView.setColor(Color.YELLOW) }
    }

    fun setWord(word: String) {
        word_tv.text = word

    }


}