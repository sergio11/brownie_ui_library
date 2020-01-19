package sanchez.sanchez.sergio.brownie.ui.components.bottomnav

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import sanchez.sanchez.sergio.brownie.R


class BezierView @JvmOverloads constructor(
    context: Context, private var fillColor: Int, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeWidth = 0f
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    private val path by lazy {
        Path()
    }

    private var bezierWidth: Float = 0.0f
    private var bezierHeight: Float = 0.0f
    private var isLinear = false

    /**
     * Build bezier view with given width and height
     *
     * @param bezierWidth  Given width
     * @param bezierHeight Given height
     * @param isLinear True, if curves are not needed
     */
    fun build(bezierWidth: Float, bezierHeight: Float, isLinear: Boolean) {
        this.bezierWidth = bezierWidth
        this.bezierHeight = bezierHeight
        this.isLinear = isLinear
    }

    /**
     * Change bezier view fill color
     *
     * @param fillColor Target color
     */
    fun changeBackgroundColor(fillColor: Int) {
        this.fillColor = fillColor
        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setBackgroundColor(ContextCompat.getColor(context, R.color.space_transparent));
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        /**
         * Set paint color to fill view
         */
        paint.color = fillColor

        /**
         * Reset path before drawing
         */
        path.reset()

        /**
         * Start point for drawing
         */
        path.moveTo(0.0f, bezierHeight)

        if(!isLinear){
            /**
             * Seth half path of bezier view
             */
            path.cubicTo(bezierWidth / 4, bezierHeight, bezierWidth / 4,
                0.0f, bezierWidth / 2, 0.0f)
            /**
             * Seth second part of bezier view
             */
            path.cubicTo((bezierWidth / 4) * 3, 0.0f, (bezierWidth / 4) * 3,
                bezierHeight, bezierWidth, bezierHeight)
        }

        /**
         * Draw our bezier view
         */
        canvas?.drawPath(path, paint)
    }
}