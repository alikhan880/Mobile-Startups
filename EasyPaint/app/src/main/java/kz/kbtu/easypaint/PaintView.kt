package kz.kbtu.easypaint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View

/**
 * Created by abakh on 10-Mar-18.
 */
class PaintView : View {

    companion object {
        const val brushSize = 10
        const val BG_COLOR = Color.WHITE
        const val DEF_COLOR = Color.BLACK
        const val TOLERANCE = 0
    }

    private var mX: Float = 0.0f
    private var mY: Float = 0.0f
    private lateinit var mPath: Path
    private var mPaint: Paint = Paint()
    private var backGroundColor = BG_COLOR
    private var paths = ArrayList<FingerPath>()
    private var strokeWidth: Int = 0
    private lateinit var myBitmap: Bitmap
    private lateinit var myCanvas: Canvas


    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        mPaint.isAntiAlias = true
        mPaint.color = DEF_COLOR
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeCap = Paint.Cap.ROUND


    }


    fun init(metrics: DisplayMetrics) {
        val width = metrics.widthPixels
        val height = metrics.heightPixels

        myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        myCanvas = Canvas(myBitmap)
        strokeWidth = brushSize

    }

    fun clear() {
        backGroundColor = BG_COLOR
        paths.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!.save()
        myCanvas.drawColor(backGroundColor)
        for (x: FingerPath in paths) {
            mPaint.strokeWidth = x.strokeWidth.toFloat()
            myCanvas.drawPath(x.path, mPaint)
        }
        canvas.drawBitmap(myBitmap, 0f, 0f, mPaint)
        canvas.restore()

    }

    fun startTouch(x: Float, y: Float) {
        mPath = Path()
        val fp = FingerPath(strokeWidth, mPath)
        paths.add(fp)
        mPath.reset()
        mPath.moveTo(x, y)
        mX = x
        mY = y

    }

    fun moveTouch(x: Float, y: Float) {
        var dx = Math.abs(x - mX)
        var dy = Math.abs(y - mY)

        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x;
            mY = y
        }
    }

    private fun upTouch() = mPath.lineTo(mX, mY)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x
        val y = event.y

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                startTouch(x, y)
                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                moveTouch(x, y)
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                upTouch()
                invalidate()
            }
        }

        return true
    }

    fun setBrushSize(paintSize: Int) {
        strokeWidth = paintSize
    }

}