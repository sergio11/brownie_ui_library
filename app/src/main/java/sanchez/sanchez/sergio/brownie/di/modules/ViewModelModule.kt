package sanchez.sanchez.sergio.brownie.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import sanchez.sanchez.sergio.brownie.di.viewmodel.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}