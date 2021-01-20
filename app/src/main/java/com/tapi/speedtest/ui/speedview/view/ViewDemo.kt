package com.tapi.speedtest.ui.speedview.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View
import com.tapi.speedtest.R

class ViewDemo @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setColorArcPaint(context.resources)
        val rectF = RectF(0f, 0f, width * 1f, height*1f)
        canvas.drawRect(rectF, mPaint)


    }

    private fun setColorArcPaint(resources: Resources) {
        val colors = arrayListOf<Int>()
        colors.add(resources.getColor(R.color.colorBlue))
        colors.add(resources.getColor(R.color.colorBlack))
        colors.add(resources.getColor(R.color.colorRed))
        val positions = FloatArray(3)

        mPaint.shader = SweepGradient(
            width / 2f,
            height /2f,
            colors.toIntArray(),
            positions
        )

        mPaint.style = Paint.Style.FILL
    }
}