package com.codecool.drawapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //val fragment = this.supportFragmentManager.findFragmentById(R.id.activity_fragment_container)
        val fragment = supportFragmentManager.findFragmentById(R.id.activity_fragment_container)
            ?.childFragmentManager?.fragments?.get(0)

        fragment?.let{
            Log.d("MainActivity()", "Fragment found! ${it.id}")
            (it as? BackButtonInterface)?.onBackButtonPressed() ?: run {Log.d(
                "MainActivity()",
                "Not implemented interface"
            )}
        } ?: run { Log.d("MainActivity", "No fragment found!")}
    }

    interface BackButtonInterface{
        fun onBackButtonPressed()
    }
}