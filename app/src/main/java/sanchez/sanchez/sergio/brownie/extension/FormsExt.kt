package sanchez.sanchez.sergio.brownie.extension

import android.widget.EditText
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout


fun EditText.content(): String = text.toString()

fun EditText.hasEmail(): Boolean = content().isEmail()

fun EditText.hasPassword(): Boolean = content().isValidPassword()

fun EditText.showError(@StringRes errorRes: Int) {
    val errorString = context.getString(errorRes)
    findParent(TextInputLayout::class.java)?.let {
        it.error = errorString
    } ?: run {
        error = errorString
    }
}

fun EditText.resetError() {
    findParent(TextInputLayout::class.java)?.let {
        it.error = null
    } ?: run {
        error = null
    }
}
