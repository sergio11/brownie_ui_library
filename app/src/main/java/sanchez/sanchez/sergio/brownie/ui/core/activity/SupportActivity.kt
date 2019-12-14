package sanchez.sanchez.sergio.brownie.ui.core.activity

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import sanchez.sanchez.sergio.brownie.extension.navController
import sanchez.sanchez.sergio.brownie.permission.IPermissionManager
import javax.inject.Inject


abstract class SupportActivity: AppCompatActivity(), IPermissionManager.OnCheckPermissionListener {


    @Inject
    protected lateinit var permissionManager: IPermissionManager

    protected var navController: NavController? = null

    /**
     * On Create
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inject Dependencies
        onInject()
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        onSetupNavigation(savedInstanceState, navController(navHostId()).also {
            navController = it
        })
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
    open fun onSetupNavigation(savedInstanceState: Bundle?, navController: NavController?){}

    /**
     * If you want to inject Dependency Injection
     * on your activity, you can override this.
     */
    abstract fun onInject()


    /**
     * Attach Base Context
     */
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    /**
     * Permission
     */
    override fun onSinglePermissionGranted(permission: String) {}
    override fun onSinglePermissionRejected(permission: String) { }
    override fun onErrorOccurred(permission: String) {}

}