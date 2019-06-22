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

fun Fragment.navigate(@IdRes action: Int) {
    NavHostFragment.findNavController(this).navigate(action)
}

fun <T> Activity.createDestination(clazz: Class<T>): ActivityNavigator.Destination =
    ActivityNavigator(this)
        .createDestination()
        .setIntent(Intent(this, clazz))


fun Activity.navigate(destination: ActivityNavigator.Destination) {
    ActivityNavigator(this).navigate(
        destination, null, null, null
    )
}


