package sanchez.sanchez.sergio.brownie.extension

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Show Short Message
 */
fun Context.showShortMessage(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


/**
 * Show Short Message
 */
fun Context.showShortMessage(@StringRes messageResId: Int) =
    showShortMessage(getString(messageResId))


/**
 * Show Long Message
 * @param message
 */
fun Context.showLongMessage(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()