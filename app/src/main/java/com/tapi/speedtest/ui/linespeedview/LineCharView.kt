package com.tapi.speedtest.ui.linespeedview

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View


class LineCharView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val lineDownload = LineDownload(this)
    val lineUpload = LineUpload(this)


    fun init() {
        lineDownload.init()
        lineUpload.init()

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        lineDownload.onDraw(canvas)
        lineUpload.onDraw(canvas)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        lineDownload.onSizeChange()
        lineUpload.onSizeChange()
    }

     fun startDrawDownload(point: PointF, i: Int,percent:Float) {
        lineDownload.startDraw(point, i,percent)
    }

     fun startDrawUpload(point: PointF, i: Int, percent:Float) {
        lineUpload.startDraw(point, i,percent)
    }

    fun setColorPathDownload() {
        lineDownload.setColorPath()
    }

    fun setColorPathUpload() {
        lineUpload.setColorPath()
    }


}