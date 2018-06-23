package me.rozkmin.direcitons.googlemaps

import com.google.android.gms.maps.model.LatLng
import me.rozkmin.directions.DirectionsSdk
import me.rozkmin.directions.EncodedPolyline
import me.rozkmin.spekbdd.BddDsl.Given
import me.rozkmin.spekbdd.BddDsl.Then
import me.rozkmin.spekbdd.BddDsl.When
import org.jetbrains.spek.api.Spek
import org.junit.Assert.assertTrue

/**
 * Created by jaroslawmichalik on 23.06.2018
 */
class ExtensionSpecification : Spek({
    Given("directions sdk") {
        val directionsSdk = DirectionsSdk("api")
        When("suggestion extensions invoked") {
            val suggestions = directionsSdk.getSuggestions(origin = LatLng(0.1, 0.1), destination = LatLng(0.2, 0.2))
            Then("there are no errors") {
                suggestions.test()
                        .assertNoErrors()
            }
        }
    }

    Given("encoded overview polyline") {
        val overviewPolyline = "mzqpH}`uxBQJ\\|Ej@xHJZvF_A`AQFo@He@^^DE|AUhCdC\\ZjAjA\\VrCxC`BdB^VbAx@RFLBb@Bj@?RCJEPUv@NTDTEZg@VOhDiAvAi@Am@\\[PO^n@@BB?tD_C|ByA|A{@bF}CBE?CAOHC?]DkAFgAR}BBGV_@v@w@n@_ApA}Bb@w@fAsAnAkAdAw@~AyA~@gA~@qAf@eA^q@HMJG\\M|@]SoAOcBYkAy@wDk@RKC"
        val encodedPolyline = EncodedPolyline(overviewPolyline)
        When("convert to latlngs") {
            val positions = encodedPolyline.getDetailedWaypointsLatLngs()
            Then("it should create non-empty list of latlngs") {
                assertTrue(positions.isNotEmpty())
            }
        }
    }
})