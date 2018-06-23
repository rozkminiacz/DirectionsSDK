package me.rozkmin.directions

data class EncodedPolyline(val points: String) {

    fun getDetailedWaypointsPositions(): List<Position> {
        val selectedPoints = mutableListOf<Position>()
        var index = 0
        val len = points.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = points[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = points[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val newLat = lat.toDouble() / 1E5
            val newLng = lng.toDouble() / 1E5

            selectedPoints.add(Position(newLat, newLng))
        }

        return selectedPoints
    }
}