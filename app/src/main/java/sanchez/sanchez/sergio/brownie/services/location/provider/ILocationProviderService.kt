package sanchez.sanchez.sergio.brownie.services.location.provider

import android.location.Location

interface ILocationProviderService {

    /**
     * Request Start Location Updates
     */
    fun requestStartLocationUpdates()

    /**
     * Request Stop Location Updates
     */
    fun requestStopLocationUpdates()

    /**
     * Request last known location
     */
    fun requestLastLocation()

    /**
     * Bind to provider
     */
    fun bind(listener: ILocationProviderListener)

    /**
     * Unbind from provider
     */
    fun unbind(listener: ILocationProviderListener)


    /**
     * Location Provider Listener
     */
    interface ILocationProviderListener {

        /**
         * On New Location
         * @param location
         */
        fun onNewLocation(location: Location)

        /**
         * On Location Updates Status Changed
         */
        fun onLocationUpdatesStatusChanged(isRequestLocationUpdatesActive: Boolean)

    }

}


