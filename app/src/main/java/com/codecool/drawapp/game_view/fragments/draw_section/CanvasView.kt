package com.codecool.drawapp.game_view.fragments.draw_section

import android.app.ActionBar
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

class CanvasView : View {
    val paths = ArrayList<Pair<Path,Paint>>()
    var lParams : ViewGroup.LayoutParams
    var brush : Paint = Paint()

    constructor(ctx: Context) : super(ctx) {
        brush = initBrush(Color.BLACK)
        lParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
    }

    private fun initBrush(color : Int) : Paint{
        val mBrush = Paint()
        mBrush.isAntiAlias = true
        mBrush.color = color
        mBrush.style = Paint.Style.STROKE
        mBrush.strokeJoin = Paint.Join.ROUND
        mBrush.strokeWidth = 8f
        return mBrush
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let{
            val pointX = it.getX()
            val pointY = it.getY()

            when (it.action){
                MotionEvent.ACTION_DOWN -> {
                    val path = Path()
                    path.moveTo(pointX,pointY)
                    paths.add(Pair(path,brush))
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    paths.last().first.lineTo(pointX,pointY)
                }
                else -> {
                    return false
                }
            }
            postInvalidate()
        }
        return false
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paths.forEach{ canvas?.drawPath(it.first,it.second) }
    }

    fun setColor(color : Int){
        brush = initBrush(color)
    }
}