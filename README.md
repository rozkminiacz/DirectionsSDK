# Directions SDK

[![Build Status](https://www.bitrise.io/app/2cd9cead18bbee3b/status.svg?token=GUQ2cooEAAZiEAK793W5mw&branch=master)](https://www.bitrise.io/app/2cd9cead18bbee3b)
[ ![Download](https://api.bintray.com/packages/rozkminiacz/Directions-Android-SDK/core/images/download.svg) ](https://bintray.com/rozkminiacz/Directions-Android-SDK/core/_latestVersion)

## What is it?
It's a library written in Kotlin to utilize Google Directions API calls and convert response to data classes.

## How do I use it?

### Add dependency


```groovy
allprojects {
	repositories {
		...
		maven { url 'https://dl.bintray.com/rozkminiacz/Directions-Android-SDK' }
	}
}
```

```groovy
implementation : 'me.rozkmin.directions:core:1.0'	
```

### Create instance of DirectionsApiClient:

```kotlin
class AnotherBoringMapActivity : Activity(){
    val directions : Directions by lazy {
        DirectionsSdk(getString(R.string.google_directions_key))
    }
}
```

### Transit options
You can specify severalDirections API request parameters:

```kotlin
val transitOptions = TransitOptions(
                mode = MODE.WALKING, 
                whatToAvoidArray = arrayOf(AVOID.FERRIES, AVOID.HIGHWAYS))
```

### Request route between points
```kotlin
class AnotherBoringMapActivity : Activity(){
    fun getSuggestions(from: LatLng, to: LatLng){
            directions.getSuggestions(
                    origin = Position(from.latitude, from.longitude),
                    destination = Position(to.latitude, from.longitude))
                    .subscribe({ t : GeocodedResponse ->
                        //process routes
                    },{
                        //error
                    })
        }
}
```

### Retrieve route steps:
```kotlin
fun processData(geocodedResponse: GeocodedResponse){
    val stepStart: Position = geocodedResponse.routes[0].legs[0].steps[0].start
    val stepEnd: Position = geocodedResponse.routes[0].legs[0].steps[0].end
}
```

### Make use of detailed polyline
```kotlin
fun processData(geocodedResponse: GeocodedResponse){
        val positions: List<Position> = geocodedResponse.routes[0].overviewPolyline.getDetailedWaypointsPositions()
}
```

## I want to improve this library!
Sure! Pull requests are welcome! Any reasonable extension will be added.

## Tools used:
* [Google Directions API](https://developers.google.com/maps/)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [OkHttp](https://github.com/square/okhttp)
* [Retrofit](http://square.github.io/retrofit/)
