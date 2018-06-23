package me.rozkmin.directions

import io.reactivex.Single

interface Directions {
    fun getSuggestions(
            origin: Position,
            destination: Position,
            options: TransitOptions = TransitOptions()): Single<GeocodedResponse>
}