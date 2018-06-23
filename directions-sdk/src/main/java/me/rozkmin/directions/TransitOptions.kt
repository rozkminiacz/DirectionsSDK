package me.rozkmin.directions

class TransitOptions(
        val sensor: Boolean = false,
        val mode: MODE = MODE.DRIVING,
        private val whatToAvoidArray: Array<AVOID> = arrayOf()) {

    fun hasWhatToAvoid() = whatToAvoidArray.isNotEmpty()

    fun whatToAvoid(): String {
        val stringBuilder = StringBuilder()
        for (a in whatToAvoidArray) {
            stringBuilder.append(a.thing)
            stringBuilder.append("|")
        }
        if (stringBuilder.isNotEmpty()) stringBuilder.deleteCharAt(stringBuilder.lastIndex)

        return stringBuilder.toString()
    }
}