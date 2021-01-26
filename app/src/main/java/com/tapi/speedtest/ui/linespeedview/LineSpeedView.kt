package com.tapi.speedtest.ui.linespeedview

import Utils
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class LineSpeedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val lineDownload = LineDownload(this)

    /*  private var newPointDownload = PointF(0f, 0f)
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
  */
    init {
        lineDownload.init()
    }

    fun init() {
        mPathRunning.moveTo(newPointDownload.x, newPointDownload.y)
        mPathPrevDownload.moveTo(newPointPrevDownload.x, newPointPrevDownload.y)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(mPathRunning, mPaintPath)
        canvas.drawPath(mPathPrevDownload, mPaintPathPrevDownload)
        canvas.drawPath(mPathLine, mPaintPath2)
        canvas.drawLine(
            oldMiddlePointDownload.x,
            oldMiddlePointDownload.y,
            width.toFloat(),
            oldMiddlePointDownload.y,
            mPaintLine
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
    }

    fun startDraw(point: PointF, ratio: Int) {
        listPoints.add(point)
        oldPointDownload = PointF(newPointDownload.x, newPointDownload.y)
        maxSpeedDownload = checkMaxPoints(point.y)
        if (listPoints.size % ratio == 0) {
            optimalDrawLineSpeedDownload()
        }
        if (isMaxPointChange) {
            mPathRunning.reset()
            if (newPointPrevDownload.x > 0f || newPointPrevDownload.y > 0f) {
                mPathRunning.moveTo(newPointPrevDownload.x, newPointPrevDownload.y)
            } else {
                mPathRunning.moveTo(newPointDownload.x, newPointDownload.y)
            }
            for (index in listPoints) {
                drawLineSpeed(index.x, point.y)
            }
            isMaxPointChange = false
        } else {
            drawLineSpeed(point.x, point.y)
        }
    }

    private fun optimalDrawLineSpeedDownload() {
        var sumY = 0f
        mPathRunning.reset()
        mPathRunning.moveTo(newPointDownload.x, newPointPrevDownload.y)
        oldPointPrevDownload = PointF(newPointPrevDownload.x, newPointPrevDownload.y)
        listPoints.forEach { point ->
            sumY += point.y
        }
        val xPos = newPointDownload.x
        val yPos =
            Utils.convertValue(0f, maxSpeedDownload, 0f, height * 1f, (sumY / listPoints.size))
        newPointPrevDownload = PointF(xPos, yPos)

        newMiddlePointPrevDownload = PointF(
            (newPointPrevDownload.x - oldPointPrevDownload.x) / 5 + oldPointPrevDownload.x,
            Utils.findYCoordinatis(
                oldPointPrevDownload,
                newPointPrevDownload,
                (newPointPrevDownload.x - oldPointPrevDownload.x) / 5 + oldPointPrevDownload.x
            )
        )

        mPathPrevDownload.lineTo(newPointPrevDownload.x, newPointPrevDownload.y)
        oldMiddlePointPrevDownload = PointF(newMiddlePointDownload.x, newMiddlePointDownload.y)
        invalidate()
    }

    private fun drawLineSpeed(xPos: Float, yPos: Float) {
        newPointDownload = PointF(
            xPos * (width / 999f),
            Utils.convertValue(0f, maxSpeedDownload, 0f, height * 1f, yPos * 1f)
        )
        newMiddlePointDownload = PointF(
            (newPointDownload.x - oldPointDownload.x) / 5 + oldPointDownload.x,
            Utils.findYCoordinatis(
                oldPointDownload,
                newPointDownload,
                (newPointDownload.x - oldPointDownload.x) / 5 + oldPointDownload.x
            )
        )
        mPathRunning.cubicTo(
            oldMiddlePointDownload.x, oldMiddlePointDownload.y,
            oldPointDownload.x, oldPointDownload.y,
            newMiddlePointDownload.x, newMiddlePointDownload.y
        )

        invalidate()
        oldMiddlePointDownload = PointF(newMiddlePointDownload.x, newMiddlePointDownload.y)


    }

    private fun checkMaxPoints(data: Float): Float {
        if (maxSpeedDownload < data) {
            isMaxPointChange = true
            return data
        }
        return maxSpeedDownload
    }

}