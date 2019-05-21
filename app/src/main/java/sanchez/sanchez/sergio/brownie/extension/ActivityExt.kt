package sanchez.sanchez.sergio.brownie.extension

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

/**
 * Add Fragment
 */
fun AppCompatActivity.addFragment(containerViewId: Int, fragment: Fragment, addToBackStack: Boolean) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.add(containerViewId, fragment)
    if (addToBackStack)
        fragmentTransaction.addToBackStack(null)
    fragmentTransaction.commit()
}

/**
 * Add Fragment
 */
fun AppCompatActivity.addFragment(containerViewId: Int, fragment: Fragment, addToBackStack: Boolean, tag: String) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.add(containerViewId, fragment)
    if (addToBackStack)
        fragmentTransaction.addToBackStack(tag)
    fragmentTransaction.commit()
}



/**
 * Get Nav Controller
 */
fun AppCompatActivity.navController(@IdRes navHostId: Int): NavController? =
    supportFragmentManager.findFragmentById(navHostId)?.let {
        return if(it is NavHostFragment) {
            it.navController
        } else {
            null
        }
    }


/**
 * Bind View
 */
fun <ViewT : View> AppCompatActivity.bindView(@IdRes idRes: Int): Lazy<ViewT> {
    return lazyUnsychronized {
        findViewById<ViewT>(idRes)
    }
}