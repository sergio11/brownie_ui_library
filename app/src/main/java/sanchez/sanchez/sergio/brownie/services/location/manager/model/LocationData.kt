package sanchez.sanchez.sergio.brownie.services.location.manager.model

data class LocationData (
    val latitude : Double,
    val longitude : Double,
    val time : Long,
    val hasAccuracyH : Boolean,
    val accuracyH : Int ,
    val hasSpeed : Boolean,
    val speed : Float ,
    val hasAltitude : Boolean,
    val altitude : Float
)