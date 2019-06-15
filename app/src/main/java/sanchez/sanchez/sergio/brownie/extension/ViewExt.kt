package sanchez.sanchez.sergio.brownie.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes


fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() { this.visibility = View.VISIBLE }

fun View.invisible() { this.visibility = View.GONE }

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

tailrec fun <T : View> View.findParent(parentType: Class<T>): T? {
    return if(parent is View)
        if (parent.javaClass == parentType) parent as T
        else  (parent as View).findParent(parentType)
    else
        null
}