package sanchez.sanchez.sergio.brownie.ui.core.navigation

import androidx.navigation.NavDirections

/**
 * A simple sealed class to handle more properly
 * navigation from a [ViewModel]
 */
sealed class NavigationCommand {
    /**
     * To
     */
    data class To(val directions: NavDirections): NavigationCommand()


    object Back: NavigationCommand()
}