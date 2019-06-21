package sanchez.sanchez.sergio.brownie.di.components

import androidx.lifecycle.ViewModelProvider
import dagger.Component
import sanchez.sanchez.sergio.brownie.di.modules.ViewModelModule
import sanchez.sanchez.sergio.brownie.di.scopes.PerFragment

@PerFragment
@Component(
    dependencies = [ ActivityComponent::class ],
    modules = [ViewModelModule::class])
interface FragmentComponent {

}