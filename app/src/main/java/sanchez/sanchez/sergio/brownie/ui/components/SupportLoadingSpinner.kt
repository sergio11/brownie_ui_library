package sanchez.sanchez.sergio.brownie.ui.components

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView

/**
Support Loading Spinner

 **/
class SupportLoadingSpinner: AppCompatImageView {

    private val ROTATE_ANIMATION_DURATION = 3600L

    /**
     * Animation
     */
    private var animation: ObjectAnimator? = null

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet?):
            super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):
            super(context, attrs, defStyleAttr)


    /**
     * On Finish Inflate
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        animation = ObjectAnimator.ofFloat(this, "rotationY", 0.0f, 360f).apply {
            duration = ROTATE_ANIMATION_DURATION
            repeatCount = ObjectAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
        }

        animation?.start()
    }


    /**
     * Set Visibility
     * @param visibility
     */
    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)

        animation?.let {
            if (visibility == View.VISIBLE)
                it.start()
            else
                it.cancel()
        }
    }

}