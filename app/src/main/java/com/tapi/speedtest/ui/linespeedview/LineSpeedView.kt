package com.tapi.speedtest.ui.linespeedview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class LineSpeedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPath = Path()
    private var maxSpeedDownload = -1
    private var isMaxStatusDownload = false

    var mAnim: ValueAnimator? = null
    private var newPoint = PointF(0f, height.toFloat())
    private var oldPoint = PointF(0f, height.toFloat())


    private val mPaintPath = Paint().apply {
        color = Color.parseColor("#FFA723")
        style = Paint.Style.STROKE
        strokeWidth = 4f
        isAntiAlias = true
    }

    private val mPaintLine = Paint().apply {
        color = Color.parseColor("#FFA723")
        style = Paint.Style.STROKE
        strokeWidth = 1f
        isAntiAlias = true
    }

    init {
        mPath.moveTo(newPoint.x, newPoint.y)
    }

    /*override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(mPath, mPaintPath)
        canvas?.drawLine(newPoint.x, newPoint.y, width.toFloat(), newPoint.y, mPaintLine)
    }*/
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val mPoint1 = PointF(width / 1.2f, height / 1.2f)
        val mPoint2 = PointF(width / 24f, height / 1.2f)
        var myPath1: Path? = Path()
        val paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        paint.color = Color.RED
        myPath1 = drawCurve(canvas, paint, mPoint1, mPoint2)
        canvas.drawPath(myPath1!!, paint)
    }

    private fun drawCurve(canvas: Canvas, paint: Paint, mPointa: PointF, mPointb: PointF): Path? {
        val myPath = Path()
        myPath.moveTo(63 * width /64f, height / 20f)
        myPath.quadTo(mPointa.x, mPointa.y, mPointb.x, mPointb.y)
        return myPath
    }

    fun startAnim() {
        mAnim = ValueAnimator.ofFloat(oldPoint.y, newPoint.y)
        mAnim?.apply {
            duration = 490
            addUpdateListener {
                newPoint.x = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    fun startDraw(x: Float, y: Float) {

        oldPoint = newPoint
        newPoint.x = x
        newPoint.y = y
        mPath.lineTo(newPoint.x, newPoint.y)
        invalidate()
    }

    fun startDraw(data: Float) {

        oldPoint = newPoint
        val rd = (0..200).random()
        newPoint.x = data * (width / 100) * 1f
        newPoint.y = rd.toFloat()
//        mPath.moveTo(63 * width / 64 * 1f, height / 10*1f)
        mPath.quadTo(oldPoint.x, oldPoint.y, newPoint.x, newPoint.y)
        invalidate()
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mAnim?.cancel()
    }

}