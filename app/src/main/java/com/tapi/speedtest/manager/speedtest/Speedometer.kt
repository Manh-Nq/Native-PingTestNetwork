package com.tapi.speedtest.manager.speedtest

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec


class Speedometer : View, SpeedChangeListener {
    // Speedometer internal state
    private var mMaxSpeed = 0f
    private var mCurrentSpeed = 0f

    // Scale drawing tools
    private var onMarkPaint: Paint? = null
    private var offMarkPaint: Paint? = null
    private var scalePaint: Paint? = null
    private var readingPaint: Paint? = null
    private var onPath: Path? = null
    private var offPath: Path? = null
    val oval = RectF()

    // Drawing colors
    private var ON_COLOR: Int = Color.argb(255, 0xff, 0xA5, 0x00)
    private var OFF_COLOR: Int = Color.argb(255, 0x3e, 0x3e, 0x3e)
    private var SCALE_COLOR: Int = Color.argb(255, 255, 255, 255)
    private var SCALE_SIZE = 14f
    private var READING_SIZE = 60f

    // Scale configuration
    private var centerX = 0f
    private var centerY = 0f
    private var radius = 0f

    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a: TypedArray = context.getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.Speedometer,
            0, 0
        )
        try {
            mMaxSpeed = a.getFloat(R.styleable.Speedometer_maxSpeed, DEFAULT_MAX_SPEED)
            mCurrentSpeed = a.getFloat(R.styleable.Speedometer_currentSpeed, 0f)
            ON_COLOR = a.getColor(R.styleable.Speedometer_onColor, ON_COLOR)
            OFF_COLOR = a.getColor(R.styleable.Speedometer_offColor, OFF_COLOR)
            SCALE_COLOR = a.getColor(R.styleable.Speedometer_scaleColor, SCALE_COLOR)
            SCALE_SIZE = a.getDimension(R.styleable.Speedometer_scaleTextSize, SCALE_SIZE)
            READING_SIZE = a.getDimension(R.styleable.Speedometer_readingTextSize, READING_SIZE)
        } finally {
            a.recycle()
        }
        initDrawingTools()
    }

    private fun initDrawingTools() {
        onMarkPaint = Paint()
        onMarkPaint.setStyle(Paint.Style.STROKE)
        onMarkPaint.setColor(ON_COLOR)
        onMarkPaint.setStrokeWidth(35f)
        onMarkPaint.setShadowLayer(5f, 0f, 0f, ON_COLOR)
        onMarkPaint.setAntiAlias(true)
        offMarkPaint = Paint(onMarkPaint)
        offMarkPaint.setColor(OFF_COLOR)
        offMarkPaint.setStyle(Paint.Style.FILL_AND_STROKE)
        offMarkPaint.setShadowLayer(0f, 0f, 0f, OFF_COLOR)
        scalePaint = Paint(offMarkPaint)
        scalePaint.setStrokeWidth(2f)
        scalePaint.setTextSize(SCALE_SIZE)
        scalePaint.setShadowLayer(5f, 0f, 0f, Color.RED)
        scalePaint.setColor(SCALE_COLOR)
        readingPaint = Paint(scalePaint)
        readingPaint.setStyle(Paint.Style.FILL_AND_STROKE)
        offMarkPaint.setShadowLayer(3f, 0f, 0f, Color.WHITE)
        readingPaint.setTextSize(65f)
        readingPaint.setTypeface(Typeface.SANS_SERIF)
        readingPaint.setColor(Color.WHITE)
        onPath = Path()
        offPath = Path()
    }

    var currentSpeed: Float
        get() = mCurrentSpeed
        set(mCurrentSpeed) {
            if (mCurrentSpeed > mMaxSpeed) this.mCurrentSpeed =
                mMaxSpeed else if (mCurrentSpeed < 0) this.mCurrentSpeed =
                0f else this.mCurrentSpeed = mCurrentSpeed
        }

    protected fun onSizeChanged(width: Int, height: Int, oldw: Int, oldh: Int) {

        // Setting up the oval area in which the arc will be drawn
        radius = if (width > height) {
            (height / 4).toFloat()
        } else {
            (width / 4).toFloat()
        }
        oval[centerX - radius, centerY - radius, centerX + radius] = centerY + radius
    }

    protected fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//		Log.d(TAG, "Width spec: " + MeasureSpec.toString(widthMeasureSpec));
//		Log.d(TAG, "Height spec: " + MeasureSpec.toString(heightMeasureSpec));
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val chosenWidth = chooseDimension(widthMode, widthSize)
        val chosenHeight = chooseDimension(heightMode, heightSize)
        val chosenDimension = Math.min(chosenWidth, chosenHeight)
        centerX = (chosenDimension / 2).toFloat()
        centerY = (chosenDimension / 2).toFloat()
        setMeasuredDimension(chosenDimension, chosenDimension)
    }

    private fun chooseDimension(mode: Int, size: Int): Int {
        return if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.EXACTLY) {
            size
        } else { // (mode == MeasureSpec.UNSPECIFIED)
            preferredSize
        }
    }

    // in case there is no size specified
    private val preferredSize: Int
        private get() = 300

    fun onDraw(canvas: Canvas) {
        drawScaleBackground(canvas)
        drawScale(canvas)
        drawLegend(canvas)
        drawReading(canvas)
    }

    /**
     * Draws the segments in their OFF state
     * @param canvas
     */
    private fun drawScaleBackground(canvas: Canvas) {
        offPath.reset()
        var i = -180
        while (i < 0) {
            offPath.addArc(oval, i, 2f)
            i += 4
        }
        canvas.drawPath(offPath, offMarkPaint)
    }

    private fun drawScale(canvas: Canvas) {
        onPath.reset()
        var i = -180
        while (i < mCurrentSpeed / mMaxSpeed * 180 - 180) {
            onPath.addArc(oval, i, 2f)
            i += 4
        }
        canvas.drawPath(onPath, onMarkPaint)
    }

    private fun drawLegend(canvas: Canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG)
        canvas.rotate(-180, centerX, centerY)
        val circle = Path()
        val halfCircumference = radius * Math.PI
        val increments = 20.0
        var i = 0
        while (i < mMaxSpeed) {
            circle.addCircle(centerX, centerY, radius, Path.Direction.CW)
            canvas.drawTextOnPath(
                String.format("%d", i),
                circle,
                (i * halfCircumference / mMaxSpeed).toFloat(),
                -30f,
                scalePaint
            )
            i += increments.toInt()
        }
        canvas.restore()
    }

    private fun drawReading(canvas: Canvas) {
        val path = Path()
        val message = String.format("%d", mCurrentSpeed.toInt())
        val widths = FloatArray(message.length)
        readingPaint.getTextWidths(message, widths)
        var advance = 0f
        for (width in widths) advance += width
        path.moveTo(centerX - advance / 2, centerY)
        path.lineTo(centerX + advance / 2, centerY)
        canvas.drawTextOnPath(message, path, 0f, 0f, readingPaint)
    }

    fun onSpeedChanged(newSpeedValue: Float) {
        currentSpeed = newSpeedValue
        this.invalidate()
    }

    companion object {
        private val TAG = Speedometer::class.java.simpleName
        const val DEFAULT_MAX_SPEED = 300f // Assuming this is km/h and you drive a super-car
    }
}