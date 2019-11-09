package sanchez.sanchez.sergio.brownie.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment


fun AppCompatActivity.navController(@IdRes navHostId: Int): NavController? =
    supportFragmentManager.findFragmentById(navHostId)?.let {
        return if(it is NavHostFragment) {
            it.navController
        } else {
            null
        }
    }

fun Fragment.navController(@IdRes navHostId: Int): NavController? =
    childFragmentManager.findFragmentById(navHostId)?.let {
        return if(it is NavHostFragment) {
            it.navController
        } else {
            null
        }
    }


fun View.createNavigateOnClickListener(@IdRes view: Int, @IdRes action: Int, args: Bundle? = null) {
    findViewById<View>(view)?.setOnClickListener(
        Navigation.createNavigateOnClickListener(action, args)
    )
}

fun View.createNavigateOnLongClickListener(@IdRes view: Int, @IdRes action: Int, args: Bundle? = null) {
    findViewById<View>(view)?.setOnLongClickListener {
        Navigation.findNavController(it).navigate(action)
        true
    }
}


fun Fragment.navigate(@IdRes action: Int, args: Bundle? = null) {
    NavHostFragment.findNavController(this).navigate(action, args)
}

fun Fragment.navigate(navDirections: NavDirections) {
    NavHostFragment.findNavController(this).navigate(navDirections)
}

fun Fragment.navigate(destination: ActivityNavigator.Destination) {
    ActivityNavigator(context!!).navigate(
        destination, null, null, null
    )
}

fun Fragment.popBackStack(@IdRes idFragmentRes: Int) {
    NavHostFragment.findNavController(this).popBackStack(idFragmentRes, true)
}

fun Fragment.popBackStack() {
    NavHostFragment.findNavController(this).popBackStack()
}

fun Fragment.navigateAndFinish(destination: ActivityNavigator.Destination) {
    navigate(destination)
    activity!!.finish()
}


fun <T> Activity.createDestination(clazz: Class<T>, args: Bundle = Bundle()): ActivityNavigator.Destination =
    ActivityNavigator(this)
        .createDestination()
        .setIntent(Intent(this, clazz).apply {
            putExtras(args)
        })


fun Activity.navigate(destination: ActivityNavigator.Destination) {
    ActivityNavigator(this).navigate(
        destination, null, null, null
    )
}

fun Activity.navigateAndFinish(destination: ActivityNavigator.Destination) {
    navigate(destination)
    finish()
}

