package com.tapi.speedtest.ui.linespeedview

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF

class LineDownload(val lineSpeedView: LineSpeedView) {
    private var newPointDownload = PointF(0f, 0f)
    private var oldPointDownload = PointF(0f, 0f)
    private var newPointPrevDownload = PointF(0f, 0f)
    private var oldPointPrevDownload = PointF(0f, 0f)
    private var oldMiddlePointDownload: PointF = PointF(0f, 0f)
    private var newMiddlePointDownload: PointF = PointF(0f, 0f)
    private var oldMiddlePointPrevDownload: PointF = PointF(0f, 0f)
    private var newMiddlePointPrevDownload: PointF = PointF(0f, 0f)


    private val mPathRunning = Path()
    private val mPathPrevDownload = Path()
    private val mPathLine = Path()
    private var listPoints = mutableListOf<PointF>()
    private var maxSpeedDownload = -1f
    private var isMaxPointChange = false

    var mWidth = 0f
    var mHeight = 0f

    private val mPaintPath = Paint().apply {
        color = Color.parseColor("#F44336")
        style = Paint.Style.STROKE
        strokeWidth = 4f
        isAntiAlias = true
    }
    private val mPaintPathPrevDownload = Paint().apply {
        color = Color.GREEN
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


    fun init() {
        mPathRunning.moveTo(newPointDownload.x, newPointDownload.y)
        mPathPrevDownload.moveTo(newPointPrevDownload.x, newPointPrevDownload.y)
    }

}