package me.rozkmin.direcitons.googlemaps

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import io.reactivex.Single
import me.rozkmin.directions.Directions
import me.rozkmin.directions.EncodedPolyline
import me.rozkmin.directions.GeocodedResponse
import me.rozkmin.directions.Position
import me.rozkmin.directions.TransitOptions

/**
 * Created by jaroslawmichalik on 23.06.2018
 */
fun Directions.getSuggestions(origin: LatLng, destination: LatLng, options: TransitOptions = TransitOptions()): Single<GeocodedResponse> {
    return getSuggestions(
            origin = Position(origin.latitude, origin.longitude),
            destination = Position(destination.latitude, destination.longitude),
            options = options)
}

fun Position.toLatLng() = LatLng(lat, lon)

fun EncodedPolyline.getDetailedWaypointsPolyline(): PolylineOptions {
    return PolylineOptions()
            .addAll(getDetailedWaypointsLatLngs())
}

fun EncodedPolyline.getDetailedWaypointsLatLngs(): List<LatLng> {
    return getDetailedWaypointsPositions().map { it.toLatLng() }
}
