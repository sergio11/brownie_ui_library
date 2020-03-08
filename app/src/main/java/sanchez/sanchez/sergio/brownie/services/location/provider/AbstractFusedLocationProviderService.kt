package sanchez.sanchez.sergio.brownie.services.location.provider

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.location.Location
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*

/**
 * Based Fused Location Provider Service
 */
abstract class AbstractFusedLocationProviderService : Service(), ILocationProviderService {

    /**
     * Used to check whether the bound activity has really gone away and not unbound as part of an
     * orientation change. We create a foreground service notification only if the former takes
     * place.
     */
    private var mChangingConfiguration = false
    private var isRequestLocationUpdatesActive = false

    private lateinit var notificationManager: NotificationManager
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationCallback: LocationCallback
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mServiceHandler: Handler

    /**
     * Local Binder
     */
    private val localBinder = LocalBinder()

    /**
     * Client Listener
     */
    private var clientListenerSet: MutableSet<ILocationProviderService.ILocationProviderListener> = mutableSetOf()

    /**
     * on Create
     */
    override fun onCreate() {
        onInject()
        super.onCreate()

        // Get Fused Location Provider
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                notifyLocation(locationResult.lastLocation)
            }
        }
        // Create Location Request
        mLocationRequest = createLocationRequest()

        // Create Service Handler
        mServiceHandler = createServiceHandler()

    }

    /**
     * On Start Command
     */
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val startedFromNotification = intent.getBooleanExtra(
            EXTRA_STARTED_FROM_NOTIFICATION,
            false)

        // We got here because the user decided to remove location updates from the notification.
        if (startedFromNotification) {
            requestStopLocationUpdates()
            stopSelf()
        }
        // Tells the system to not try to recreate the service after it has been killed.
        return START_NOT_STICKY
    }

    /**
     * On Configuration Changed
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mChangingConfiguration = true
    }

    /**
     * On Bind
     */
    override fun onBind(intent: Intent): IBinder? {
        stopForeground(true)
        mChangingConfiguration = false
        return localBinder
    }

    /**
     * On Rebind
     */
    override fun onRebind(intent: Intent) {
        stopForeground(true)
        mChangingConfiguration = false
        super.onRebind(intent)
    }

    /**
     * On Unbind
     * Last client unbound from service therefore starting service
     * again as foreground
     */
    override fun onUnbind(intent: Intent): Boolean {
        if (!mChangingConfiguration && isRequestLocationUpdatesActive)
            startForeground(FOREGROUND_NOTIFICATION_ID, getNotification())
        return true
    }

    /**
     * On Destroy
     */
    override fun onDestroy() {
        mServiceHandler.removeCallbacksAndMessages(null)
        if(isRequestLocationUpdatesActive)
            requestStopLocationUpdates()
    }

    /**
     * Location Service API
     */

    /**
     * Makes a request for location updates.
     */
    override fun requestStartLocationUpdates() {
        Log.d(SERVICE_TAG, "requestStartLocationUpdates CALLED")
        startService(getServiceIntent())

        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback, Looper.myLooper()).addOnSuccessListener {
                updateRequestLocationUpdateStatus(true)
            }.addOnFailureListener {
                Log.d(SERVICE_TAG, "Request Location Updates failed")
            }
        } catch (unlikely: SecurityException) {
            Log.d(SERVICE_TAG, "Request Location Updates Security Exception")
        }
    }

    /**
     * Removes location updates.
     */
     override fun requestStopLocationUpdates() {
        Log.d(SERVICE_TAG, "Request Stop Location Updates")
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback).addOnSuccessListener {
                updateRequestLocationUpdateStatus(false)
            }.addOnFailureListener {
                Log.d(SERVICE_TAG,"Request Stop Location Updates failed")
            }
            stopSelf()
        } catch (unlikely: SecurityException) {
            Log.d(SERVICE_TAG, "Request Stop Location Updates Security Exception")
        }
    }

    override fun requestLastLocation() {
        try {
            mFusedLocationClient.lastLocation
                .addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        notifyLocation(task.result!!)
                    } else {
                    }
                }
        } catch (unlikely: SecurityException) {
        }
    }

    override fun bind(listener: ILocationProviderService.ILocationProviderListener) {
        clientListenerSet.add(listener)
    }

    override fun unbind(listener: ILocationProviderService.ILocationProviderListener) {
        clientListenerSet.remove(listener)
    }


    abstract fun onInject()
    abstract fun getServiceIntent(): Intent
    abstract fun getNotification(): Notification

    /**
     * Private Methods
     */

    /**
     * Update Request Location Update status
     */
    private fun updateRequestLocationUpdateStatus(isActive: Boolean) {
        isRequestLocationUpdatesActive = isActive
        clientListenerSet.forEach {
            it.onLocationUpdatesStatusChanged(isRequestLocationUpdatesActive)
        }
    }

    /**
     * Notify Location
     * @param location
     */
    private fun notifyLocation(location: Location) {
        Log.d(SERVICE_TAG, "Notify Location CALLED")
        clientListenerSet.forEach {
            it.onNewLocation(location)
        }
    }

    /**
     * Create Location Request Format
     */
    private fun createLocationRequest(): LocationRequest =
        LocationRequest.create().apply {
            interval =
                UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval =
                FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

    /**
     * Create Service Handler
     */
    private fun createServiceHandler(): Handler =
        Handler(HandlerThread(SERVICE_TAG).also { it.start() }.looper)

    /**
     * Class used for the client Binder.  Since this service runs in the same process as its
     * clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : LocationProviderBinder() {

        override fun provideService(): ILocationProviderService =
            this@AbstractFusedLocationProviderService
    }

    companion object {

        const val SERVICE_TAG = "FUSED_LOCATION_PROVIDER"
        const val FOREGROUND_NOTIFICATION_ID = 23453


        /**
         * Configuration Params
         */

        /**
         * The desired interval for location updates. Inexact. Updates may be more or less frequent.
         */
        const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000

        /**
         * The fastest rate for active location updates. Updates will never be more frequent
         * than this value.
         */
        const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2


        /**
         * Service Params
         */

        /**
         * Extra Started From Notification
         */
        const val EXTRA_STARTED_FROM_NOTIFICATION = "STARTED_FROM_NOTIFICATION"

    }

}