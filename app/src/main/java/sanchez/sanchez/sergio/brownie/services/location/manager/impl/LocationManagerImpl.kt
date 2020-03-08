package sanchez.sanchez.sergio.brownie.services.location.manager.impl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.location.Location
import android.os.IBinder
import sanchez.sanchez.sergio.brownie.utils.IObservable
import sanchez.sanchez.sergio.brownie.utils.Observable
import sanchez.sanchez.sergio.brownie.services.location.ext.toLocationData
import sanchez.sanchez.sergio.brownie.services.location.manager.ILocationManager
import sanchez.sanchez.sergio.brownie.services.location.manager.model.GpsStatsData
import sanchez.sanchez.sergio.brownie.services.location.manager.model.LocationData
import sanchez.sanchez.sergio.brownie.utils.observable
import sanchez.sanchez.sergio.brownie.services.location.provider.ILocationProviderService
import sanchez.sanchez.sergio.brownie.services.location.provider.LocationProviderBinder


/**
 * Location Manager implementation
 */
class LocationManagerImpl(private val providerServiceIntent: Intent):
    ILocationManager, ILocationProviderService.ILocationProviderListener,
    IObservable<ILocationManager> by Observable() {

    // Observable last location
    override var lastLocationObs: LocationData? by observable(null)

    // Gps Stats Obs
    override var gpsStatsObs: GpsStatsData? by observable(null)

    // Is Request Location Updates Active Observable
    override var isRequestLocationUpdatesActiveObs: Boolean by observable(false)

    // A reference to the service used to get location updates.
    private var mProviderService: ILocationProviderService? = null

    // Tracks the bound state of the service.
    private var mBound = false

    // Monitors the state of the connection to the service.
    private val mServiceConnection = object : ServiceConnection {

        /**
         * On Service Connected
         */
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as LocationProviderBinder
            mProviderService = binder.provideService()
            mBound = true
            onConnectedToLocationProviderService(mProviderService!!)
        }

        /**
         * On Service Disconnected
         */
        override fun onServiceDisconnected(name: ComponentName) {
            mProviderService = null
            mBound = false
        }
    }


    /**
     * Makes a request for location updates.
     */
    override fun requestStartLocationUpdates() {
        mProviderService?.requestStartLocationUpdates()
    }

    /**
     * Makes a request for stop location updates
     */
    override fun requestStopLocationUpdates() {
        mProviderService?.requestStopLocationUpdates()
    }

    /**
     * Request Last Location
     */
    override fun requestLastLocation() {
        mProviderService?.requestLastLocation()
    }

    /**
     *  Bind to the service. If the service is in foreground mode, this signals to the service
     * that since this activity is in the foreground, the service can exit foreground mode.
     */
    override fun bind(context: Context) {
        context.bindService(providerServiceIntent,
            mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    /**
     * Unbind from the service. This signals to the service that this activity is no longer
     * in the foreground, and the service can respond by promoting itself to a foreground
     * service.
     */
    override fun unBind(context: Context) {
        if (mBound) {
            context.unbindService(mServiceConnection)
            mBound = false
        }
    }

    /**
     * On New Location
     * @param location
     */
    override fun onNewLocation(location: Location) {
        val locationData = location.toLocationData()
        lastLocationObs = locationData
        updateGpsStats(locationData)
    }

    override fun onLocationUpdatesStatusChanged(isRequestLocationUpdatesActive: Boolean) {
        isRequestLocationUpdatesActiveObs = isRequestLocationUpdatesActive
    }

    /**
     * Private Methods
     */

    private fun onConnectedToLocationProviderService(provider: ILocationProviderService) {
        provider.bind(this)
        provider.requestLastLocation()
    }

    /**
     * Update Gps Stats
     * @param locationData
     */
    private fun updateGpsStats(locationData: LocationData) {
        gpsStatsObs = gpsStatsObs?.let {
            val locationCount = it.locationsCount + 1
            val initialTimestamp = it.initialTimestamp
            val currTimestamp = System.currentTimeMillis()
            val prevTimestamp = it.currTimestamp

            val currDurationSeconds = ((currTimestamp - initialTimestamp) / 1000).toInt()
            val lastDurationSeconds = ((currTimestamp - prevTimestamp) / 1000).toInt()

            val avgSecondsBtwLocations = 1.0f * currDurationSeconds / locationCount
            val maxSecondsBtwLocations = if (it.maxSecondsBtwLocations < lastDurationSeconds)
                lastDurationSeconds
            else
                it.maxSecondsBtwLocations

            GpsStatsData(
                locationsCount = locationCount,
                prevLocation = it.currLocation,
                currLocation = locationData,
                avgSecondsBtwLocations = avgSecondsBtwLocations,
                maxSecondsBtwLocations = maxSecondsBtwLocations,
                initialTimestamp = initialTimestamp,
                prevTimestamp = prevTimestamp,
                currTimestamp = currTimestamp,
                altitude = if(locationData.hasAltitude) locationData.altitude.toInt() else 0,
                accuracyH = if(locationData.hasAccuracyH) locationData.accuracyH else 0,
                maxHAccuracy = if(locationData.hasAccuracyH &&
                    it.maxHAccuracy < locationData.accuracyH)
                    locationData.accuracyH
                else
                    it.maxHAccuracy,
                speed = if(locationData.hasSpeed) locationData.speed else 0.0f,
                maxSpeedMS = if(locationData.hasSpeed &&
                    it.maxSpeedMS < locationData.speed )
                    locationData.speed
                else it.maxSpeedMS

            )


        } ?: kotlin.run {

            val now = System.currentTimeMillis()

            GpsStatsData(
                locationsCount = 1,
                currLocation = locationData,
                initialTimestamp = now,
                currTimestamp = now,
                altitude = if(locationData.hasAltitude) locationData.altitude.toInt() else 0,
                accuracyH = if(locationData.hasAccuracyH) locationData.accuracyH else 0,
                maxHAccuracy = if(locationData.hasAccuracyH) locationData.accuracyH else 0,
                speed = if(locationData.hasSpeed) locationData.speed else 0.0f,
                maxSpeedMS = if(locationData.hasSpeed) locationData.speed else 0.0f
            )
        }
    }

}