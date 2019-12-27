package sanchez.sanchez.sergio.brownie.extension

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import sanchez.sanchez.sergio.brownie.ui.core.activity.SupportActivity

fun SupportActivity.addFragment(containerViewId: Int, fragment: Fragment, addToBackStack: Boolean) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.add(containerViewId, fragment)
    if (addToBackStack)
        fragmentTransaction.addToBackStack(null)
    fragmentTransaction.commit()
}

fun SupportActivity.addFragment(containerViewId: Int, fragment: Fragment, addToBackStack: Boolean, tag: String) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.add(containerViewId, fragment)
    if (addToBackStack)
        fragmentTransaction.addToBackStack(tag)
    fragmentTransaction.commit()
}

fun <ViewT : View> AppCompatActivity.bindView(@IdRes idRes: Int): Lazy<ViewT> {
    return lazyUnsychronized {
        findViewById<ViewT>(idRes)
    }
}

/**
 * Share Simple Content thought Android Sharesheet
 * @param textTitle
 * @param textContent
 */
fun Activity.shareSimpleContent(textTitle: String, textContent: String) {
    startActivity(Intent.createChooser(Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TITLE, textTitle)
        putExtra(Intent.EXTRA_TEXT, textContent)
        type = "text/plain"
    }, null))
}


