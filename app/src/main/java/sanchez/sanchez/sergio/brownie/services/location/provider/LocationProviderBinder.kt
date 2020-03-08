package sanchez.sanchez.sergio.brownie.services.location.provider

import android.os.Binder

/**
 * Location Provider Binder
 */
abstract class LocationProviderBinder: Binder() {
    abstract fun provideService(): ILocationProviderService
}