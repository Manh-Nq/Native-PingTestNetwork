package com.tapi.speedtest.ui.connectview

import android.animation.ValueAnimator
import android.graphics.*
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.tapi.speedtest.R
import com.tapi.speedtest.`object`.Constance
import kotlinx.coroutines.delay

class TextDrawer(val connectView: ConnectView) {

    private var oldRatio: Float = 0.8f
    private var newRatio: Float = 0.9f
    private var mPaintText: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val typeFace: Typeface =
        ResourcesCompat.getFont(connectView.context, R.font.opensans_bold)!!
    private val colorText: Int = ContextCompat.getColor(connectView.context, R.color.colorProgress2)
    private var text: String = "CONNECT"
    private val rectText = RectF(0f, 0f, 0f, 0f)
    private var ratioScale: Float = 0.8f
    private var mScaleAnimation: ValueAnimator? = null

    fun onSizeChanged() {
        rectText.left = connectView.paddingLeft.toFloat()
        rectText.right =
            connectView.width - (connectView.paddingLeft + connectView.paddingRight).toFloat()
        rectText.top = connectView.paddingTop.toFloat()
        rectText.bottom =
            connectView.height - (connectView.paddingTop + connectView.paddingBottom).toFloat()
    }

    fun drawText(canvas: Canvas) {
        canvas.save()
        canvas.scale(ratioScale, ratioScale, rectText.width() / 2f, rectText.height() / 2f)
        mPaintText.color = colorText
        mPaintText.style = Paint.Style.FILL
        val rect = Rect()
        mPaintText.typeface = typeFace
        mPaintText.textSize = dp2px(Constance.TEXT_SIZE_CONNECTVIEW).toFloat()
        mPaintText.getTextBounds(text, 0, text.length, rect)

        canvas.drawText(
            text,
            rectText.width() / 2f - rect.width() / 2,
            rectText.height() / 2f + rect.height() / 2f,
            mPaintText
        )

        canvas.restore()
    }

    private fun dp2px(dp: Float): Int {
        return (connectView.context.resources.displayMetrics.density * dp + 0.5f).toInt()
    }


    suspend fun startAnim() {
        val listScale = Constance.LIST_SCALE
        while (true) {
            for (item in listScale) {
                delay(20)
                ratioScale = item
                connectView.updateView()
                startAnimationText(item)
            }
        }
    }

    private fun startAnimationText(item: Float) {
        newRatio = item
        mScaleAnimation?.cancel()
        mScaleAnimation = ValueAnimator.ofFloat(oldRatio, newRatio)
        mScaleAnimation?.apply {
            duration = 40
            addUpdateListener {
                ratioScale = it.animatedValue as Float
                Log.d("TAG", "startAnimationText: ${it.animatedValue}")
                connectView.updateView()
            }
            start()
        }
        oldRatio = newRatio
    }

    fun onRelease() {
        mScaleAnimation?.cancel()
    }

}