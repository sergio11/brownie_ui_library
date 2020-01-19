package sanchez.sanchez.sergio.brownie.ui.components.bottomnav.badge


import android.view.View
import android.widget.TextView
import sanchez.sanchez.sergio.brownie.ui.components.bottomnav.model.BadgeItem
import android.widget.RelativeLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListenerAdapter
import sanchez.sanchez.sergio.brownie.extension.visible
import sanchez.sanchez.sergio.brownie.extension.gone
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.ShapeDrawable
import sanchez.sanchez.sergio.brownie.R


object BadgeHelper {

    fun showBadge(
        view: RelativeLayout,
        badgeItem: BadgeItem,
        shouldShowBadgeWithNinePlus: Boolean
    ) {
        view.visible()
        val badgeTextView = view.findViewById(R.id.badge_text_view) as TextView
        if (shouldShowBadgeWithNinePlus)
            badgeTextView.text = badgeItem.getBadgeText()
        else
            badgeTextView.text = badgeItem.getFullBadgeText()

        view.scaleX = 0f
        view.scaleY = 0f

        ViewCompat.animate(view)
            .setDuration(200)
            .scaleX(1.0f)
            .scaleY(1.0f)
            .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                override fun onAnimationEnd(view: View) {
                    view.visible()
                }
            })
            .start()
    }

    fun hideBadge(view: View) {
        ViewCompat.animate(view)
            .setDuration(200)
            .scaleX(0f)
            .scaleY(0f)
            .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                override fun onAnimationEnd(view: View?) {
                    view?.gone()
                }
            })
            .start()
    }

    fun forceShowBadge(
        view: RelativeLayout,
        badgeItem: BadgeItem,
        shouldShowBadgeWithNinePlus: Boolean
    ) {
        view.visible()
        view.background = makeShapeDrawable(badgeItem.badgeColor)
        val badgeTextView = view.findViewById(R.id.badge_text_view) as TextView
        if (shouldShowBadgeWithNinePlus)
            badgeTextView.text = badgeItem.getBadgeText()
        else
            badgeTextView.text = badgeItem.getFullBadgeText()
    }

    /**
     * Make circle drawable for badge background
     *
     * @param color background color
     * @return return colored circle drawable
     */
    fun makeShapeDrawable(color: Int): ShapeDrawable =
        ShapeDrawable(OvalShape()).apply {
            intrinsicWidth = 10
            intrinsicHeight = 10
            paint.color = color
        }
}