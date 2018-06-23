package me.rozkmin.googlemapsdraw

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.rozkmin.direcitons.googlemaps.getDetailedWaypointsPolyline
import me.rozkmin.direcitons.googlemaps.getSuggestions
import me.rozkmin.directions.AVOID
import me.rozkmin.directions.DirectionsSdk
import me.rozkmin.directions.Directions
import me.rozkmin.directions.GeocodedResponse
import me.rozkmin.directions.MODE
import me.rozkmin.directions.Position
import me.rozkmin.directions.TransitOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val krk = LatLng(50.052, 19.944)
        map.addMarker(
                MarkerOptions()
                        .position(krk)
                        .draggable(false)
                        .title("Marker in Kraków"))

        map.addMarker(
                MarkerOptions()
                        .position(krk)
                        .draggable(true)
                        .title("Drag me")
        )

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(krk, 13f))

        map.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragEnd(marker: Marker) {
                drawRoute(marker.position, krk)
            }

            override fun onMarkerDragStart(p0: Marker?) {}

            override fun onMarkerDrag(p0: Marker?) {}
        })
    }

    fun drawRoute(from: LatLng, to: LatLng) {
        val sdk: Directions = DirectionsSdk(getString(R.string.google_directions_key))

        val transitOptions = TransitOptions(
                mode = MODE.WALKING,
                whatToAvoidArray = arrayOf(AVOID.FERRIES, AVOID.HIGHWAYS))

        sdk.getSuggestions(from, to, transitOptions)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.routes.forEach {
                        map.addPolyline(it.overviewPolyline.getDetailedWaypointsPolyline())
                    }
                }, {
                    //ignore
                })
    }

    fun getSuggestions(from: LatLng, to: LatLng){
        val directions: Directions = DirectionsSdk(getString(R.string.google_directions_key))

        directions.getSuggestions(
                origin = Position(from.latitude, from.longitude),
                destination = Position(to.latitude, from.longitude))
                .subscribe({ t : GeocodedResponse ->
                    t.routes.map {
                        it.overviewPolyline.getDetailedWaypointsPositions()
                    }
                },{
                    //error
                })
    }

    fun processData(geocodedResponse: GeocodedResponse){
        val stepStart: Position = geocodedResponse.routes[0].legs[0].steps[0].start
        val stepEnd: Position = geocodedResponse.routes[0].legs[0].steps[0].end
    }
}
