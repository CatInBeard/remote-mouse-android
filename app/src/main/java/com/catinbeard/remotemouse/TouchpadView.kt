package com.catinbeard.remotemouse

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.lang.Math.pow
import kotlin.math.sqrt

class TouchpadView : View {
    private var previousX = 0f
    private var previousY = 0f

    interface OnTouchpadMoveListener {
        fun onTouchpadMove(deltaX: Float, deltaY: Float)
        fun onClick()
    }

    private var listener: OnTouchpadMoveListener? = null

    fun setOnTouchpadMoveListener(listener: OnTouchpadMoveListener?) {
        this.listener = listener
    }


    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )



    override fun performClick(): Boolean {
        if (listener != null) {
            listener!!.onClick()
        }
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val currentX = event.x
        val currentY = event.y


        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                previousX = currentX
                previousY = currentY
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val deltaX = currentX - previousX
                val deltaY = currentY - previousY

                if(Math.sqrt((deltaX * deltaX + deltaY * deltaY).toDouble()) > 20){
                    if (listener != null) {
                        listener!!.onTouchpadMove(deltaX, deltaY)
                    }
                    previousX = currentX
                    previousY = currentY
                }
            }

            MotionEvent.ACTION_UP -> {
                previousX = 0f
                previousY = 0f
            }
        }

        return super.onTouchEvent(event)
    }
}