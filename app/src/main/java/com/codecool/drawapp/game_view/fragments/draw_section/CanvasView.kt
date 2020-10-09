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
    var lParams : ViewGroup.LayoutParams
    var path = Path()
    var brush : Paint = Paint()

    constructor(ctx: Context) : super(ctx) {
        brush.isAntiAlias = true
        brush.color = Color.BLACK
        brush.style = Paint.Style.STROKE
        brush.strokeJoin = Paint.Join.ROUND
        brush.strokeWidth = 8f

        lParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let{
            val pointX = it.getX()
            val pointY = it.getY()

            when (it.action){
                MotionEvent.ACTION_DOWN -> {
                    path.moveTo(pointX,pointY)
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    path.lineTo(pointX,pointY)
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
        canvas?.drawPath(path,brush)
    }
}