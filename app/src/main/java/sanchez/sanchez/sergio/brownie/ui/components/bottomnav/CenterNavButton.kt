package sanchez.sanchez.sergio.brownie.ui.components.bottomnav

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CenterNavButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FloatingActionButton(context, attrs, defStyleAttr) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        val result = super.onTouchEvent(ev)
        if (!result) {
            if (ev?.action == MotionEvent.ACTION_UP) {
                cancelLongPress()
            }
            isPressed = false
        }
        return result
    }
}