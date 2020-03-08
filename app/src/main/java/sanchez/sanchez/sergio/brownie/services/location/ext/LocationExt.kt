package sanchez.sanchez.sergio.brownie.services.location.ext

import android.location.Location
import sanchez.sanchez.sergio.brownie.services.location.manager.model.LocationData

fun Location.toLocationData(): LocationData =
    LocationData(
        this.latitude,
        this.longitude,
        this.time,
        this.hasAccuracy(),
        this.accuracy.toInt(),
        this.hasSpeed(),
        this.speed,
        this.hasAltitude(),
        this.altitude.toFloat()
    )