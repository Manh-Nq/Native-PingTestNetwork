package com.tapi.speedtest.ui.linespeedview

import Utils
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


class LineSpeedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var oldMiddlePoint: PointF = PointF(0f, height.toFloat())
    private lateinit var newMiddlePoint: PointF

    private val mPath = Path()
    private val mPathLine = Path()
    var mAnim: ValueAnimator? = null
    private var newPoint = PointF(0f, height.toFloat())
    private var oldPoint = PointF(0f, height.toFloat())
    private var listPoints = mutableListOf<PointF>()

    private val mPaintPath = Paint().apply {
        color = Color.parseColor("#F44336")
        style = Paint.Style.STROKE
        strokeWidth = 4f
        isAntiAlias = true
    }
    private val mPaintPath2 = Paint().apply {
        color = Color.parseColor("#7DF6FF")
        style = Paint.Style.STROKE
        strokeWidth = 4f
        isAntiAlias = true
    }

    private val mPaintLine = Paint().apply {
        color = Color.parseColor("#F44336")
        style = Paint.Style.STROKE
        strokeWidth = 1f
        isAntiAlias = true
    }

    init {
        mPath.moveTo(newPoint.x, newPoint.y)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPath(mPath, mPaintPath)
        canvas.drawPath(mPathLine, mPaintPath2)
        canvas.drawLine(
            oldMiddlePoint.x,
            oldMiddlePoint.y,
            width.toFloat(),
            oldMiddlePoint.y,
            mPaintLine
        )
    }

/*
    fun startDraw(x: Float, y: Float) {
        oldPoint = newPoint
        newPoint.x = x
        newPoint.y = y
        mPath.lineTo(newPoint.x, newPoint.y)
        invalidate()
    }*/


    fun startDraw(data: Float) {
        oldPoint.x = newPoint.x
        oldPoint.y = newPoint.y
        /*    Log.d(
                "TAG",
                "drawLineCurved: \n old point : ${oldPoint.x},  ${oldPoint.y}"
            )*/
        val rd = (1..200).random()
        newPoint.x = data * (width / 19f)
        newPoint.y = rd.toFloat()
        listPoints.add(newPoint)
        newMiddlePoint = PointF(
            (newPoint.x - oldPoint.x) / 10 + oldPoint.x,
            Utils.findYCoordinatis(oldPoint, newPoint, (newPoint.x - oldPoint.x) / 10 + oldPoint.x)
        )

        mPath.cubicTo(
            oldMiddlePoint.x, oldMiddlePoint.y,
            oldPoint.x, oldPoint.y,
            newMiddlePoint.x, newMiddlePoint.y
        )

//        mPathLine.lineTo(newPoint.x, newPoint.y)

        invalidate()
        oldMiddlePoint = PointF(newMiddlePoint.x, newMiddlePoint.y)
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mAnim?.cancel()
    }

}