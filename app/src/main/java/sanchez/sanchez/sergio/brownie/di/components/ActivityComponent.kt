package sanchez.sanchez.sergio.brownie.di.components

import dagger.Component
import sanchez.sanchez.sergio.brownie.di.modules.ActivityModule
import sanchez.sanchez.sergio.brownie.di.scopes.PerActivity
import sanchez.sanchez.sergio.brownie.permission.IPermissionManager
import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import dagger.Provides
import sanchez.sanchez.sergio.brownie.di.modules.ViewModelModule

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 */
@PerActivity
@Component(
    dependencies = [ ApplicationComponent::class ],
            modules = [ ActivityModule::class, ViewModelModule::class ] )
interface ActivityComponent {

    //Exposed to sub-graphs.
    fun activity(): Activity
    fun permissionManager(): IPermissionManager
}