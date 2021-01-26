package com.tapi.speedtest.ui.linespeedview

import Utils
import android.graphics.*
import android.util.Log
import androidx.core.content.ContextCompat
import com.tapi.speedtest.R

class LineUpload(val lineSpeedView: LineCharView) {

    val TAG = LineDownload::class.java.name
    private var newPoint = PointF(0f, 0f)
    private var oldPoint = PointF(0f, 0f)
    private var newPointPrev = PointF(0f, 0f)
    private var oldPointPrev = PointF(0f, 0f)
    private var oldMiddlePointPrev: PointF = PointF(0f, 0f)
    private var newMiddlePointPrev: PointF = PointF(0f, 0f)


    private val mPathRunning = Path()
    private val mPathPrev = Path()
    private var listPoints = mutableListOf<PointF>()
    private var maxSpeed = -1f
    private var isMaxPointChange = false

    var mWidth = 0f
    var mHeight = 0f

    private val mPaintPath = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 4f
        isAntiAlias = true
    }
    private val mPaintPathPrev = Paint().apply {
        color = Color.parseColor("#F44336")
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


    fun init() {
        mPathRunning.moveTo(newPoint.x, newPoint.y)
        mPathPrev.moveTo(newPointPrev.x, newPointPrev.y)
    }

    fun onSizeChange() {
        mWidth = lineSpeedView.width.toFloat()
        mHeight = lineSpeedView.height.toFloat()
    }

    fun onDraw(canvas: Canvas) {
        canvas.drawPath(mPathRunning, mPaintPath)
        canvas.drawPath(mPathPrev, mPaintPathPrev)
        canvas.drawLine(
            newPointPrev.x,
            newPointPrev.y,
            mWidth,
            newPointPrev.y,
            mPaintLine
        )
    }


    fun startDraw(point: PointF, ratio: Int, percent: Float) {
        listPoints.add(point)
        oldPoint = PointF(newPoint.x, newPoint.y)
        maxSpeed = checkMaxPoints(point.y)
        if (listPoints.size % ratio == 0) {
            optimalDrawLineSpeed()
        }
        if (isMaxPointChange) {
            mPathRunning.reset()
            mPathRunning.moveTo(newPoint.x, newPoint.y)
            for (index in listPoints) {
                drawLineSpeed(index.x, point.y, percent)
//                optimalDrawLineSpeed()
            }
            isMaxPointChange = false
        } else {
            drawLineSpeed(point.x, point.y, percent)
        }
    }

    private fun optimalDrawLineSpeed() {
        var sumY = 0f
        mPathRunning.reset()
        mPathRunning.moveTo(newPoint.x, newPointPrev.y)
        oldPointPrev = PointF(newPointPrev.x, newPointPrev.y)
        listPoints.forEach { point ->
            sumY += point.y
        }
        val xPos = newPoint.x
        val yPos =
            mHeight - Utils.convertValue(0f, maxSpeed, 0f, mHeight * 1f, (sumY / listPoints.size))
        newPointPrev = PointF(xPos, yPos)

        newMiddlePointPrev = PointF(
            (newPointPrev.x - oldPointPrev.x) / 5 + oldPointPrev.x,
            Utils.findYCoordinatis(
                oldPointPrev,
                newPointPrev,
                (newPointPrev.x - oldPointPrev.x) / 5 + oldPointPrev.x
            )
        )

        mPathPrev.cubicTo(
            oldMiddlePointPrev.x,
            oldMiddlePointPrev.y,
            oldPointPrev.x,
            oldPointPrev.y,
            newMiddlePointPrev.x,
            newMiddlePointPrev.y
        )
        oldMiddlePointPrev = PointF(newMiddlePointPrev.x, newMiddlePointPrev.y)
        lineSpeedView.invalidate()
    }

    private fun drawLineSpeed(xPos: Float, yPos: Float, percent: Float) {
        newPoint = PointF(
//           percent * mWidth / 100,
            xPos * (mWidth / 999f),
            Utils.convertValue(0f, maxSpeed, 0f, mHeight * 1f, yPos * 1f)
        )
        Log.d(
            TAG,
            "  drawLineSpeed: newPoint $newPoint - yposition $yPos  - maxSpeed $maxSpeed  mHeight $mHeight"
        )
        mPathRunning.lineTo(
            newPoint.x, newPoint.y
        )
        lineSpeedView.invalidate()
    }

    private fun checkMaxPoints(data: Float): Float {
        if (maxSpeed < data) {
            isMaxPointChange = true
            return data
        }
        return maxSpeed
    }

    fun setColorPath() {
        mPaintPath.color = ContextCompat.getColor(lineSpeedView.context, R.color.whitealpha)
    }


}