package sanchez.sanchez.sergio.brownie.extension

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import sanchez.sanchez.sergio.brownie.ui.dialogs.impl.NoticeDialogFragment
import sanchez.sanchez.sergio.brownie.ui.dialogs.impl.ConfirmationDialogFragment
import sanchez.sanchez.sergio.brownie.ui.dialogs.impl.ProgressDialogFragment


/**
 * Add Fragment
 * @param containerViewId
 * @param fragment
 * @param addToBackStack
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
 * @param containerViewId
 * @param fragment
 * @param addToBackStack
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
 * @param navHostId
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
 * @param idRes
 */
fun <ViewT : View> AppCompatActivity.bindView(@IdRes idRes: Int): Lazy<ViewT> {
    return lazyUnsychronized {
        findViewById<ViewT>(idRes)
    }
}

/**
 * Show Notice Dialog
 * @param title
 * @param isSuccess
 * @param noticeDialogListener
 */
fun AppCompatActivity.showNoticeDialog(title: String, isSuccess: Boolean = true, noticeDialogListener: NoticeDialogFragment.NoticeDialogListener? = null) {
    NoticeDialogFragment.showDialog(this, title, isSuccess,  noticeDialogListener)
}

/**
 * Show Notice Dialog
 * @param stringResId
 * @param isSuccess
 * @param noticeDialogListener
 */
fun AppCompatActivity.showNoticeDialog(@StringRes stringResId: Int, isSuccess: Boolean = true, noticeDialogListener: NoticeDialogFragment.NoticeDialogListener? = null) {
    showNoticeDialog(getString(stringResId), isSuccess, noticeDialogListener)
}


/**
 * Show Progress Dialog
 * @param title
 */
fun AppCompatActivity.showProgressDialog(title: String) {
    ProgressDialogFragment.showDialog(this, title)
}

/**
 * Show Progress Dialog
 * @param stringResId
 */
fun AppCompatActivity.showProgressDialog(@StringRes stringResId: Int) {
    showProgressDialog(getString(stringResId))
}

/**
 * Hide Progress Dialog
 */
fun AppCompatActivity.hideProgressDialog() {
    ProgressDialogFragment.cancelCurrent()
}

/**
 * Show Confirmation Dialog
 * @param title
 * @param confirmationDialogListener
 */
fun AppCompatActivity.showConfirmationDialog(title: String, confirmationDialogListener: ConfirmationDialogFragment.ConfirmationDialogListener? = null) {
    ConfirmationDialogFragment.showDialog(this, title, confirmationDialogListener)
}


/**
 * Show Confirmation Dialog
 * @param stringResId
 * @param confirmationDialogListener
 */
fun AppCompatActivity.showConfirmationDialog(@StringRes stringResId: Int, confirmationDialogListener: ConfirmationDialogFragment.ConfirmationDialogListener? = null) {
    showConfirmationDialog(getString(stringResId))
}


