package com.tapi.speedtest.ui.linespeedview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class LineSpeedView @JvmOverloads constructor(
    val mContext: Context,
    val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(mContext, attrs, defStyleAttr) {
    private var path: Path
    var xPos = 0f
    val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var yPos = height / 2f

    init {
         path = Path()
        path.moveTo(0f, height / 2f)
    }
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.color = Color.RED
       

      
        path.lineTo(width / 2f, height / 2f)


        /* path.moveTo(200f, 200f)*/

        canvas.drawPath(path, mPaint)
    }


    fun setPos(x: Float, y: Float) {
        this.xPos = x
        this.yPos = y
        invalidate()
    }

}