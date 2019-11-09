package sanchez.sanchez.sergio.brownie.extension

import android.animation.ArgbEvaluator
import android.widget.ImageView
import android.animation.ValueAnimator
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


/**
 * Change given image view tint with animation
 * @param fromColor start animation from color
 * @param toColor   final color
 */
fun ImageView.changeTintWithAnimation(fromColor: Int, toColor: Int) {
    ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor)
        .apply {
            addUpdateListener { animator -> setColorFilter(animator.animatedValue as Int) }
            duration = 150
        }.start()
}

/**
 * Change given image view tint

 * @param color     tint color
 */
fun ImageView.changeImageViewTintWithColorRes(@ColorRes colorRes: Int) {
    setColorFilter(ContextCompat.getColor(context, colorRes))
}


/**
 * Change given image view tint

 * @param color     tint color
 */
fun ImageView.changeImageViewTint(@ColorInt color: Int) {
    setColorFilter(color)
}