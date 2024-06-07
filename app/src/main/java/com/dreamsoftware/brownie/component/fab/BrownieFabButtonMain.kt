package com.dreamsoftware.brownie.component.fab

/**
 * Represents the main floating action button (FAB) with an icon and optional rotation.
 * The main FAB is the primary action button that can be expanded to reveal sub-items.
 */
interface BrownieFabButtonMain {
    val iconRes: Int
    val iconRotate: Float?
}

/**
 * Implementation of [BrownieFabButtonMain] interface.
 *
 * @property iconRes The [ImageVector] representing the icon to be displayed on the main FAB.
 * @property iconRotate The optional rotation angle for the main FAB icon. If null, the icon will not be rotated.
 */
private class BrownieFabButtonMainImpl(
    override val iconRes: Int,
    override val iconRotate: Float?
) : BrownieFabButtonMain

/**
 * Creates a new instance of [BrownieFabButtonMain] with the provided icon and optional rotation.
 *
 * @param iconRes The [ImageVector] representing the icon to be displayed on the main FAB.
 * @param iconRotate The optional rotation angle for the main FAB icon. If null, the icon will not be rotated.
 * @return A new instance of [BrownieFabButtonMain] with the specified icon and rotation.
 */
fun BrownieFabButtonMain(iconRes: Int, iconRotate: Float = 45f): BrownieFabButtonMain =
    BrownieFabButtonMainImpl(iconRes, iconRotate)