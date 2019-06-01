package sanchez.sanchez.sergio.brownie.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import sanchez.sanchez.sergio.brownie.di.scopes.PerActivity
import sanchez.sanchez.sergio.brownie.di.viewmodel.ViewModelFactory

/**
 * View Model Module
 */
@Module
abstract class ViewModelModule {

    /**
     * View Model Factory
     */
    @PerActivity
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}