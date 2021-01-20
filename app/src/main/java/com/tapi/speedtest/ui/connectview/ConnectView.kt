package com.tapi.speedtest.ui.connectview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.tapi.speedtest.R
import com.tapi.speedtest.ui.connectview.animations.ConnectAnimation

class ConnectView(private val mContext: Context, attrs: AttributeSet?) :
    View(mContext, attrs) {
    private var text: String = "CONNECT"
    private var mPercent = 75f
    private var mBorderStrokeWidth = 0f
    private var mBgColor = -0x1e1e1f
    private var mStartAngle = 0f
    private var mFgColorStart = -0x1c00
    private var mFgColorEnd = -0xb800

    private var mPaintText: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPaintCircle1: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var typeFace = ResourcesCompat.getFont(context, R.font.opensans_bold)!!

    private val colorText: Int
    private val animationView = ConnectAnimation(this)
    private val borderDrawer = BorderDrawer(this)

    init {
        colorText = ContextCompat.getColor(context, R.color.colorProgress2)
        mPaintText.color = colorText
        mPaintCircle1.style = Paint.Style.FILL
        mPaintCircle1.style = Paint.Style.FILL
        mPaintText.style = Paint.Style.FILL

    }

    private fun dp2px(dp: Float): Int {
        return (mContext.resources.displayMetrics.density * dp + 0.5f).toInt()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        borderDrawer.onDraw(canvas)

        val rect = Rect()
        mPaintText.typeface = typeFace
        mPaintText.textSize = dp2px(40f).toFloat()
        mPaintText.getTextBounds(text, 0, text.length, rect)

        canvas.drawText(
            text,
            width / 2f - rect.width() / 2,
            height / 2f + rect.height() / 2f,
            mPaintText
        )

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        borderDrawer.onSizeChanged()
    }

    fun refreshTheLayout() {
        invalidate()
        requestLayout()
    }

    var startAngle: Float
        get() = mStartAngle
        set(mStartAngle) {
            this.mStartAngle = mStartAngle + 270
            refreshTheLayout()
        }

    fun setText(text: String) {
        this.text = text
    }

    fun updateView() {
        invalidate()
    }

    init {
        val a = mContext.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ColorfulRingProgressView,
            0, 0
        )
        try {
            mBgColor = a.getColor(R.styleable.ColorfulRingProgressView_bgColor, -0x1e1e1f)
            mFgColorEnd = a.getColor(R.styleable.ColorfulRingProgressView_fgColorEnd, -0xb800)
            mFgColorStart = a.getColor(R.styleable.ColorfulRingProgressView_fgColorStart, -0x1c00)
            mPercent = a.getFloat(R.styleable.ColorfulRingProgressView_percent, 75f)
            mStartAngle = a.getFloat(R.styleable.ColorfulRingProgressView_startAngle, 0f) + 270
            mBorderStrokeWidth = a.getDimensionPixelSize(
                R.styleable.ColorfulRingProgressView_strokeWidth,
                dp2px(10f)
            ).toFloat()
            borderDrawer.changeStrokeWidth(10f)
        } finally {
            a.recycle()
        }
    }

    suspend fun startAnim() {
        borderDrawer.start()
        animationView.startTextAnim()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        borderDrawer.onRelease()
    }


}