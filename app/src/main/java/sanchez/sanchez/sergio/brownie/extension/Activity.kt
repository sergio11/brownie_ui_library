package sanchez.sanchez.sergio.brownie.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

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