package me.rozkmin.directions

data class Leg(val steps: List<Step>, val duration: Duration = Duration("", 0), val distance: Distance = Distance("", 0))