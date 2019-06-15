package sanchez.sanchez.sergio.brownie.extension

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun Context.showShortMessage(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.showShortMessage(@StringRes messageResId: Int) =
    showShortMessage(getString(messageResId))

fun Context.showLongMessage(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()