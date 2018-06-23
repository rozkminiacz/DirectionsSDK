package me.rozkmin.directions

import com.google.gson.annotations.SerializedName

data class RouteElement(val legs: List<Leg>, @SerializedName("overview_polyline") val overviewPolyline: EncodedPolyline)