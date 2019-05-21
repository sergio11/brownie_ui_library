package sanchez.sanchez.sergio.brownie.extension

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment


/**
 * Bind View
 */
fun <ViewT : View> Fragment.bindView(@IdRes idRes: Int): Lazy<ViewT?> {
    return lazyUnsychronized {
        view?.findViewById<ViewT>(idRes)
    }
}
