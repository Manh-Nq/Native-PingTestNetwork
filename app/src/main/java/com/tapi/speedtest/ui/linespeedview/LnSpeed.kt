package com.tapi.speedtest.ui.linespeedview

import Utils
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


class LnSpeed @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {

        mPaint.color = Color.RED
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 3f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLineCurved(canvas)

    }

    private val pointPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        pointPaint.style = Paint.Style.FILL
    }

    private fun drawLineCurved(canvas: Canvas) {
        val path = Path()
        val point1 = PointF(0f, height / 2f)
        path.moveTo(point1.x, point1.y)

        val point2 = PointF(150f, height / 3f)
        val point3 = PointF(300f, height / 2f)
        val point4 = PointF(450f, height / 4f)
        val point5 = PointF(600f, height / 2f)

        val pointMiddle12 =
            PointF(
                (point2.x - point1.x) / 2 + point1.x,
                Utils.findYCoordinatis(point1, point2, (point2.x - point1.x) / 2 + point1.x)
            )

        val pointMiddle23 =
            PointF(
                (point3.x - point2.x) / 2 + point2.x ,
                Utils.findYCoordinatis(point2, point3, (point3.x - point2.x) / 2 + point2.x)
            )

        val pointMiddle34 =
            PointF(
                (point4.x - point3.x) / 2 + point3.x,
                Utils.findYCoordinatis(point3, point4, (point4.x - point3.x) / 2 + point3.x)
            )

        val pointMiddle45 =
            PointF(
                (point5.x - point4.x) / 2 + point4.x,
                Utils.findYCoordinatis(point4, point5, (point5.x - point4.x) / 2 + point4.x)
            )

        Log.d(
            "TAG",
            "drawLineCurved: ${point1.x},  ${point1.y},  ${pointMiddle12.x},  ${pointMiddle12.y}, ${point2.x},  ${point2.y}"
        )
        pointPaint.color = Color.CYAN
        canvas.drawCircle(pointMiddle12.x, pointMiddle12.y, 40f, pointPaint)
        pointPaint.color = Color.MAGENTA
        canvas.drawCircle(
            point2.x,
            point2.y, 40f, pointPaint
        )
        pointPaint.color = Color.GREEN
        canvas.drawCircle(
            pointMiddle23.x,
            pointMiddle23.y, 40f, pointPaint
        )
        path.reset()
        path.moveTo(pointMiddle12.x, pointMiddle12.y)
        path.cubicTo(
            pointMiddle12.x,
            pointMiddle12.y,
            point2.x,
            point2.y,
            pointMiddle23.x,
            pointMiddle23.y

        )
        path.cubicTo(
            pointMiddle23.x,
            pointMiddle23.y,
            point3.x,
            point3.y,
            pointMiddle34.x,
            pointMiddle34.y
        )

        path.cubicTo(
            pointMiddle34.x,
            pointMiddle34.y,
            point4.x,
            point4.y,
            pointMiddle45.x,
            pointMiddle45.y
        )

        path.lineTo(point1.x, point1.y)
        path.lineTo(point2.x, point2.y)
        path.lineTo(point3.x, point3.y)
        path.lineTo(point4.x, point4.y)
        path.lineTo(point5.x, point5.y)

        canvas.drawPath(path, mPaint)

    }

}