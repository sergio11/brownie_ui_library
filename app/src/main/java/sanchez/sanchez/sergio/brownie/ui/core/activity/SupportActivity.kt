package sanchez.sanchez.sergio.brownie.ui.core.activity

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import sanchez.sanchez.sergio.brownie.extension.navController


/**
 * Support Activity
 */
abstract class SupportActivity: AppCompatActivity() {

    /**
     * On Create
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inject Dependencies
        onInject()
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        onSetupNavigation(navController(navHostId()))
    }

    /**
     * Layout Id
     */
    @LayoutRes
    abstract fun layoutId(): Int

    /**
     * Nav Host Id
     */
    @IdRes
    abstract fun navHostId(): Int


    /**
     * On Setup Navigation
     */
    open fun onSetupNavigation(navController: NavController?){}

    /**
     * If you want to inject Dependency Injection
     * on your activity, you can override this.
     */
    open fun onInject() {}


    /**
     * Attach Base Context
     */
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

}