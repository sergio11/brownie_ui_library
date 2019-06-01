package sanchez.sanchez.sergio.brownie.di.modules

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import sanchez.sanchez.sergio.brownie.permission.impl.PermissionManagerImpl
import sanchez.sanchez.sergio.brownie.permission.IPermissionManager
import dagger.Provides
import android.app.Activity
import sanchez.sanchez.sergio.brownie.di.scopes.PerActivity

/**
 * A module to wrap all the dependencies of an activity
 */
@Module
class ActivityModule constructor(private val activity: AppCompatActivity) {

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    fun provideActivity(): Activity {
        return this.activity
    }

    /**
     * Expose the IPermissionManager to dependents in the graph.
     */
    @Provides
    @PerActivity
    fun providePermissionManager(activity: AppCompatActivity): IPermissionManager {
        return PermissionManagerImpl(activity)
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    fun provideActivityCompat(): AppCompatActivity {
        return this.activity
    }

}