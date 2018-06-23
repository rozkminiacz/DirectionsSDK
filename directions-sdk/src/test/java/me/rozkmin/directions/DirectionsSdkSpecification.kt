package me.rozkmin.directions

import me.rozkmin.spekbdd.BddDsl.Given
import me.rozkmin.spekbdd.BddDsl.Then
import me.rozkmin.spekbdd.BddDsl.When
import org.jetbrains.spek.api.Spek
import org.junit.Assert.assertTrue

/**
 * Created by jaroslawmichalik on 23.06.2018
 */
class DirectionsSdkSpecification : Spek({
    Given("directions api key not provided") {
        val directions = DirectionsSdk("")
        When("request invoked") {
            val suggestionsSingle = directions.getSuggestions(Position(0.0, 0.0), Position(0.0, 0.0))
            Then("it should throw error") {
                suggestionsSingle.test()
                        .assertNoValues()
                        .assertError(KeyEmptyError)
            }
        }
    }

    Given("request builder") {
        When("build request with default transit options") {
            val requestBuilder = RequestBuilder(Position(0.1, 0.1), Position(0.2, 0.2), TransitOptions(), "key")
            Then("it should construct valid url") {
                val requestParams = requestBuilder.build()
                assertTrue(requestParams == "json?origin=0.1,0.1&destination=0.2,0.2&sensor=false&mode=driving&key=key")
            }
        }

        When("build request with walking transit options") {
            val requestBuilder = RequestBuilder(Position(0.1, 0.1), Position(0.2, 0.2), TransitOptions(mode = MODE.WALKING), "key")
            Then("it should construct valid url") {
                val requestParams = requestBuilder.build()
                assertTrue(requestParams == "json?origin=0.1,0.1&destination=0.2,0.2&sensor=false&mode=walking&key=key")
            }
        }

        When("build request with driving transit options and avoid highways") {
            val requestBuilder = RequestBuilder(Position(0.1, 0.1), Position(0.2, 0.2), TransitOptions(mode = MODE.WALKING, whatToAvoidArray = arrayOf(AVOID.HIGHWAYS)), "key")
            Then("it should construct valid url") {
                val requestParams = requestBuilder.build()
                assertTrue(requestParams == "json?origin=0.1,0.1&destination=0.2,0.2&sensor=false&mode=walking&avoid=highways&key=key")
            }
        }

        When("build request with driving transit options and avoid highways and ferries") {
            val requestBuilder = RequestBuilder(Position(0.1, 0.1), Position(0.2, 0.2), TransitOptions(mode = MODE.WALKING, whatToAvoidArray = arrayOf(AVOID.HIGHWAYS, AVOID.FERRIES)), "key")
            Then("it should construct valid url") {
                val requestParams = requestBuilder.build()
                assertTrue(requestParams == "json?origin=0.1,0.1&destination=0.2,0.2&sensor=false&mode=walking&avoid=highways|ferries&key=key")
            }
        }
    }

    Given("encoded overview polyline") {
        val overviewPolyline = "mzqpH}`uxBQJ\\|Ej@xHJZvF_A`AQFo@He@^^DE|AUhCdC\\ZjAjA\\VrCxC`BdB^VbAx@RFLBb@Bj@?RCJEPUv@NTDTEZg@VOhDiAvAi@Am@\\[PO^n@@BB?tD_C|ByA|A{@bF}CBE?CAOHC?]DkAFgAR}BBGV_@v@w@n@_ApA}Bb@w@fAsAnAkAdAw@~AyA~@gA~@qAf@eA^q@HMJG\\M|@]SoAOcBYkAy@wDk@RKC"
        val encodedPolyline = EncodedPolyline(overviewPolyline)
        When("convert to waypoints") {
            val positions = encodedPolyline.getDetailedWaypointsPositions()
            Then("it should create non-empty list") {
                assertTrue(positions.isNotEmpty())
            }
        }
    }
})