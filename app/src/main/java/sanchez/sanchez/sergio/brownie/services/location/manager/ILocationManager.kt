package sanchez.sanchez.sergio.brownie.services.location.manager

import android.content.Context
import sanchez.sanchez.sergio.brownie.utils.IObservable
import sanchez.sanchez.sergio.brownie.services.location.manager.model.GpsStatsData
import sanchez.sanchez.sergio.brownie.services.location.manager.model.LocationData

/**
 * Location Manager interface
 */
interface ILocationManager:
    IObservable<ILocationManager> {

    /**
     * Last location, register listener for get updates from this
     */
    var lastLocationObs: LocationData?

    /**
     * Gps Stats, register listener for get updates
     */
    var gpsStatsObs: GpsStatsData?

    /**
     * Is Request Location Updates Active, register listener for get updates
     */
    var isRequestLocationUpdatesActiveObs: Boolean

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
     * Bind to location events
     */
    fun bind(context: Context)

    /**
     * unbind
     */
    fun unBind(context: Context)


}