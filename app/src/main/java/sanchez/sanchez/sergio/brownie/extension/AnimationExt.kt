package sanchez.sanchez.sergio.brownie.extension

import android.view.View
import android.view.animation.*
import androidx.annotation.AnimRes
import sanchez.sanchez.sergio.brownie.R


/**
 * Default Values
 */
private const val DEFAULT_AMPLITUDE = 0.2
private const val DEFAULT_FREQUENCY = 20.0
private const val DEFAULT_FROM_SCALE_X = 1.0f
private const val DEFAULT_FROM_SCALE_Y = 1.0f
private const val DEFAULT_TO_SCALE_X = 2.0f
private const val DEFAULT_TO_SCALE_Y = 2.0f
private const val DEFAULT_DURATION = 1000L

/**
 * Bounce Animation
 * @param amplitude (default 0.2)
 * @param frequency (default 20.0)
 */
fun View.bounce(amplitude: Double = DEFAULT_AMPLITUDE, frequency: Double = DEFAULT_FREQUENCY) {
    startAnimation( AnimationUtils.loadAnimation(context, R.anim.bounce).apply {
        interpolator = BounceInterpolator(amplitude, frequency)
    })
}

/**
 * Scale Up Animation
 * @param fromScaleX (default 1.0f)
 * @param fromScaleY (default 1.0f)
 * @param toScaleX (default 2.0f)
 * @param toScaleY (default 2.0f)
 */
fun View.scaleUp(fromScaleX: Float = DEFAULT_FROM_SCALE_X, fromScaleY: Float = DEFAULT_FROM_SCALE_Y,
                 toScaleX: Float = DEFAULT_TO_SCALE_X, toScaleY: Float = DEFAULT_TO_SCALE_Y
) {
    startAnimation( ScaleAnimation(fromScaleX, toScaleX, fromScaleY, toScaleY, 50f, 50f).apply {
        fillAfter = true
        duration = DEFAULT_DURATION
    })
}

/**
 * Scale Down Animation
 * @param fromScaleX (default 1.0f)
 * @param fromScaleY (default 1.0f)
 * @param toScaleX (default 2.0f)
 * @param toScaleY (default 2.0f)
 */
fun View.scaleDown(fromScaleX: Float = DEFAULT_TO_SCALE_X, fromScaleY: Float = DEFAULT_TO_SCALE_Y,
                   toScaleX: Float = DEFAULT_FROM_SCALE_X, toScaleY: Float = DEFAULT_FROM_SCALE_Y
) {
    startAnimation( ScaleAnimation(fromScaleX, toScaleX, fromScaleY, toScaleY, 50f, 50f).apply {
        fillAfter = true
        duration = DEFAULT_DURATION
    })
}

/**
 * Translate Animation
 * @param fromXDelta
 * @param fromYDelta
 * @param toXDelta
 * @param toYDelta
 * @param listener
 */
fun View.translateAnimation(fromXDelta: Float, fromYDelta: Float,
                            toXDelta: Float, toYDelta: Float, animDuration: Long = DEFAULT_DURATION,
                            listener: Animation.AnimationListener? = null){
    startAnimation(TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta).apply {
        setAnimationListener(listener)
        fillAfter = true
        duration = animDuration
    })
}


/**
 * Start Animation Helper
 * @param animRes
 * @param listener
 */
fun View.startAnimation(@AnimRes animRes: Int, listener: Animation.AnimationListener? = null) {
    startAnimation(AnimationUtils.loadAnimation(context, animRes).apply {
        setAnimationListener(listener)
        fillAfter = true
    })
}


/**
 * Translate Animator
 */
fun View.translateAnimator(
    toXDelta: Float,
    toYDelta: Float,
    toZDelta: Float,
    duration: Long = DEFAULT_DURATION) {
    animate().setDuration(duration)
        .translationX(toXDelta)
        .translationY(toYDelta)
        .translationZ(toZDelta)
        .start()
}


/**
 * Scale Animator
 * @param scaleX
 * @param scaleY
 * @param duration
 */
fun View.scaleAnimator(
    scaleX: Float,
    scaleY: Float,
    duration: Long = DEFAULT_DURATION) {
    animate().setDuration(duration)
        .scaleX(scaleX)
        .scaleY(scaleY)
        .start()
}


private class BounceInterpolator
constructor(private val amplitude: Double, private val frequency: Double): Interpolator {

    override fun getInterpolation(time: Float): Float =
        ((-1 * Math.pow(Math.E, -time/ amplitude) *
                Math.cos(frequency * time) + 1).toFloat())

}