package me.rozkmin.directions

class RequestBuilder(private val origin: Position, private val dest: Position, private val transitOptions: TransitOptions, private val apiKey: String) {
    fun build(): String {
        return StringBuilder().apply {
            append("json?")
            append("origin=${origin.lat},${origin.lon}")
            append("&destination=${dest.lat},${dest.lon}")
            append("&sensor=${transitOptions.sensor}")
            append("&mode=${transitOptions.mode.type}")

            if (transitOptions.hasWhatToAvoid()) append("&avoid=${transitOptions.whatToAvoid()}")
            append("&key=$apiKey")
        }.toString()
    }
}