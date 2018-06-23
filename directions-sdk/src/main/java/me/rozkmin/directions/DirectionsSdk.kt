package me.rozkmin.directions

import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DirectionsSdk(private val apiKey: String) : Directions {

    private val googleMapsApi: DirectionsApi by lazy {
        retrofit(OkHttpClient.Builder()
                .build()).create(DirectionsApi::class.java)
    }

    private fun retrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/directions/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    override fun getSuggestions(
            origin: Position, destination: Position, options: TransitOptions): Single<GeocodedResponse> =
            when {
                apiKey.isBlank() -> Single.error(KeyEmptyError)
                else -> googleMapsApi.getGeocodeDirectionsResponse(
                        requestOptions = RequestBuilder(
                                origin = origin,
                                dest = destination,
                                transitOptions = options,
                                apiKey = apiKey).build())
            }
}