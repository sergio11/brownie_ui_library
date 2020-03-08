package sanchez.sanchez.sergio.brownie.services.location.manager.model

data class GpsStatsData(
    val locationsCount : Int = 0,
    val currLocation : LocationData? = null,
    val prevLocation : LocationData? = null,
    val maxSecondsBtwLocations : Int = 0,
    val avgSecondsBtwLocations: Float = 0.0f,
    val accuracyH : Int = 0,
    val maxHAccuracy : Int = 0,
    val speed: Float = 0.0f,
    val maxSpeedMS: Float = 0.0f,
    val altitude : Int = 0,
    val initialTimestamp : Long = 0,
    val finalTimestamp : Long = 0,
    val currTimestamp : Long =0,
    val prevTimestamp : Long = 0
)