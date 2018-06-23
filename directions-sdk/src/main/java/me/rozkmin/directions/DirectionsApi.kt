package me.rozkmin.directions

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by jaroslawmichalik on 03.05.2018
 */
interface DirectionsApi {
    /**
     * Make request to directions API
     */
    @GET
    fun getGeocodeDirectionsResponse(@Url requestOptions: String): Single<GeocodedResponse>
}