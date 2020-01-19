package sanchez.sanchez.sergio.brownie.ui.components.bottomnav.model

class BadgeItem constructor(
    val badgeIndex: Int,
    val badgeText: Int,
    val badgeColor: Int
) {

    fun getFullBadgeText() = badgeText.toString()

    fun getBadgeText(): String =
        if (badgeText > BADGE_TEXT_MAX_NUMBER) {
            "$BADGE_TEXT_MAX_NUMBER+"
        } else {
            badgeText.toString()
        }


    companion object {
        const val BADGE_TEXT_MAX_NUMBER = 9
    }
}