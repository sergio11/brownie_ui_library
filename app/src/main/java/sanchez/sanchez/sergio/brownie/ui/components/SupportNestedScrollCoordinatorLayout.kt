package sanchez.sanchez.sergio.brownie.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Size
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat


/**

 * This means it can act as a nested scrolling child, and thus forward scroll events to parents.
 *
 * This is useful for nested Coordinators, e.g. for inner fragments in a parent activity. If this is used,
 * scrolls to the inner fragment will trigger scroll animations (e.g. {@code AppBarLayout}s, fabs, ...)
 * to the parent activity as well.
 *
 * This works by *not* reinventing the wheel and reusing the same nested scrolling logic implemented
 * by behaviors. There is a dummy view inside the sheet that is capable of getting nested scrolling
 * callbacks, and forward them to the *outer* behavior that they normally would never reach.
 */
class SupportNestedScrollCoordinatorLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr), NestedScrollingChild  {

    private val helper: NestedScrollingChildHelper by lazy {
        NestedScrollingChildHelper(this)
    }
    private var dummyBehavior: DummyBehavior<View>? = null

    init {
        isNestedScrollingEnabled = true
        // Add a dummy view that will receive inner touch events.
        val dummyView = View(context)
        dummyBehavior = DummyBehavior()
        // I *think* this is needed for dummyView to be identified as "topmost" and receive events
        // before any other view.
        ViewCompat.setElevation(dummyView, ViewCompat.getElevation(this))
        // Make sure it does not fit windows, or it will consume insets before the AppBarLayout.
        dummyView.fitsSystemWindows = false
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        params.behavior = dummyBehavior
        addView(dummyView, params)
    }

    /**
     * Sets the pass mode for this coordinator.
     * @see .PASS_MODE_BOTH
     *
     * @see .PASS_MODE_PARENT_FIRST
     *
     *
     * @param mode desired pass mode for scroll events.
     */
    fun setPassMode(mode: Int) {
        dummyBehavior?.setPassMode(mode)
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        helper.isNestedScrollingEnabled = enabled
    }

    override fun isNestedScrollingEnabled(): Boolean
            = helper.isNestedScrollingEnabled ?: false

    override fun startNestedScroll(axes: Int): Boolean =
        helper.startNestedScroll(axes) ?: false

    override fun stopNestedScroll() {
        helper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean =
        helper.hasNestedScrollingParent()

    /**
     * Dispatch Nested Scroll
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param offsetInWindow
     */
    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        @Size(value = 2) offsetInWindow: IntArray?
    ): Boolean =
        helper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow
        )


    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        @Size(value = 2)
            consumed: IntArray?,
        @Size(
            value = 2
        ) offsetInWindow: IntArray?
    ): Boolean =
        helper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow) ?: false

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean =
        helper.dispatchNestedFling(velocityX, velocityY, consumed) ?: false

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean =
        helper.dispatchNestedPreFling(velocityX, velocityY) ?: false


    /**
     * This behavior is assigned to our dummy, MATCH_PARENT view inside this bottom sheet layout.
     * Through this behavior the dummy view can listen to touch/scroll events.
     * Our goal is to propagate them to the parent stream.
     *
     * It has to be done manually because by default CoordinatorLayouts don't propagate scroll events
     * to their parent. This is bad for CoordinatorLayouts inside other CoordinatorLayouts, since
     * the coordination works relies heavily on scroll events.
     *
     * @param <DummyView> make sure it's not a nested-scrolling-enabled view or this will break.
    </DummyView> */
    private class DummyBehavior<DummyView : View> internal constructor() :
        CoordinatorLayout.Behavior<DummyView>() {

        private var mode = PASS_MODE_BOTH
        private val cache = IntArray(2)

        internal fun setPassMode(mode: Int) {
            this.mode = mode
        }

        override fun onStartNestedScroll(
            coordinatorLayout: CoordinatorLayout,
            child: DummyView,
            directTargetChild: View,
            target: View,
            axes: Int,
            type: Int
        ): Boolean {
            val sheet = coordinatorLayout as SupportNestedScrollCoordinatorLayout
            // If we want to catch, catch.
            return sheet.startNestedScroll(axes)
        }


        override fun onStopNestedScroll(
            coordinatorLayout: CoordinatorLayout,
            child: DummyView,
            target: View,
            type: Int
        ) {
            val sheet = coordinatorLayout as SupportNestedScrollCoordinatorLayout
            sheet.stopNestedScroll()
        }

        override fun onNestedPreScroll(
            coordinatorLayout: CoordinatorLayout,
            child: DummyView,
            target: View,
            dx: Int,
            dy: Int,
            consumed: IntArray,
            type: Int
        ) {
            val sheet = coordinatorLayout as SupportNestedScrollCoordinatorLayout
            if (mode == PASS_MODE_PARENT_FIRST) {
                sheet.dispatchNestedPreScroll(dx, dy, consumed, null)
            } else if (mode == PASS_MODE_BOTH) {
                // Don't let sheet consume the original int.
                cache[0] = consumed[0]
                cache[1] = consumed[1]
                sheet.dispatchNestedPreScroll(dx, dy, cache, null)
            }
        }


        override fun onNestedPreFling(
            coordinatorLayout: CoordinatorLayout,
            child: DummyView,
            target: View,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val sheet = coordinatorLayout as SupportNestedScrollCoordinatorLayout
            val s = sheet.dispatchNestedPreFling(velocityX, velocityY)
            return if (mode == PASS_MODE_PARENT_FIRST) {
                s
            } else false
        }

        // onNestedScroll and onNestedFling are not needed.
    }


    companion object {
        /**
         * Constant for [.setPassMode]. When this is selected, scroll events are
         * passed to the parent stream and, at the same time, to this Coordinator childs.
         */
        const val PASS_MODE_BOTH = 0

        /**
         * Constant for [.setPassMode]. When this is selected, scroll events are
         * passed to the parent stream and, if not consumed, they go on to this Coordinator childs.
         */
        const val PASS_MODE_PARENT_FIRST = 1
    }

}