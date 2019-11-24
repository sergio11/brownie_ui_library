package sanchez.sanchez.sergio.brownie.extension

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import sanchez.sanchez.sergio.brownie.models.Event
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import android.R.attr.description




fun <ViewT : View> Fragment.bindView(@IdRes idRes: Int): Lazy<ViewT?> {
    return lazyUnsychronized {
        view?.findViewById<ViewT>(idRes)
    }
}

fun Fragment.showSnackbar(
    description: String,
    timeLength: Int,
    actionText: String? = null,
    onActionClickListener: View.OnClickListener? = null,
    snackbarCallback: Snackbar.Callback? = null ) {
    activity?.let {
        Snackbar.make(it.findViewById<View>(android.R.id.content), description, timeLength).apply {
            if(onActionClickListener != null)
                setAction(actionText, onActionClickListener)
            if(snackbarCallback != null)
            addCallback(snackbarCallback)
        }.also {snackBar ->
            snackBar.show()
        }
    }
}

fun Fragment.setupSnackbar(lifecycleOwner: LifecycleOwner, snackbarEvent: LiveData<Event<Int>>, timeLength: Int) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { res ->
            context?.let { showSnackbar(it.getString(res), timeLength) }
        }
    })
}

