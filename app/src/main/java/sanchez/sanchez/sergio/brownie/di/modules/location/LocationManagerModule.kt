package sanchez.sanchez.sergio.brownie.di.modules.location

import android.content.Intent
import dagger.Module
import dagger.Provides
import sanchez.sanchez.sergio.brownie.di.modules.notifications.NotificationsModule
import sanchez.sanchez.sergio.brownie.di.scopes.PerApplication
import sanchez.sanchez.sergio.brownie.services.location.manager.ILocationManager
import sanchez.sanchez.sergio.brownie.services.location.manager.impl.LocationManagerImpl
import javax.inject.Named

/**
 * Location Manager Module
 */
@Module(includes = [ NotificationsModule::class ])
class LocationManagerModule {

    /**
     * Provide Location Manager
     * @param providerServiceIntent
     */
    @Provides
    @PerApplication
    fun provideLocationManager(
        @Named(LOCATION_PROVIDER_SERVICE_INTENT)
            providerServiceIntent: Intent): ILocationManager =
        LocationManagerImpl(
            providerServiceIntent
        )


    companion object {
        const val LOCATION_PROVIDER_SERVICE_INTENT = "LOCATION_PROVIDER_INTENT"
    }

}