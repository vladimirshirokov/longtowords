package ru.livemaster.longtowords

import java.util.*

val ZERO = "zero"
val ONE = "one"
val TWO = "two"
val NUM = "num"
val THOUSAND = "thousand"
val MILLION = "million"
val BILLION = "billion"
val TRILLION = "trillion"
val QUADRILLION = "quadrillion"
val MINUS = "minus"

class LongToWords {
    private var one: List<String>
    private var two: List<String>
    private var thousand: List<String>
    private var million: List<String>
    private var billion: List<String>
    private var trillion: List<String>
    private var quadrillion: List<String>

    private var bundle: ResourceBundle

    init {
        bundle = ResourceBundle.getBundle("number_names", Locale.getDefault());
        one = bundle.getString(ONE).split(",")
        two = bundle.getString(TWO).split(",")
        thousand = bundle.getString(THOUSAND).split(",")
        million = bundle.getString(MILLION).split(",")
        billion = bundle.getString(BILLION).split(",")
        trillion = bundle.getString(TRILLION).split(",")
        quadrillion = bundle.getString(QUADRILLION).split(",")
    }

    private fun getName(index: Int, type: Int): String {
        when (type) {
            1 -> {
                return thousand[index];
            }
            2 -> {
                return million[index];
            }
            3 -> {
                return billion[index];
            }
            4 -> {
                return trillion[index];
            }
            5 -> {
                return quadrillion[index];
            }
        }
        return ""
    }

    private fun convertUnit(unit: Long, type: Int): String {
        var result = ""
        if (unit == 1L) {
            if (type == 1) {
                result += one[1]
            } else {
                result += one[0]
            }
        } else if (unit == 2L) {
            if (type == 1) {
                result += two[1]
            } else {
                result += two[0]
            }
        }
        return result
    }

    private fun convertPartToWords(value: Long, type: Int): String {
        if (value == 0L) return ""
        var hundreds = value / 100 * 100
        var tens = value % 100
        var result = ""
        var endIndex = 0;
        if (hundreds > 0) {
            result = bundle.getString(NUM + hundreds) + " "
            endIndex = 2
        }
        if (tens < 20 && tens > 2) {
            result += bundle.getString(NUM + tens) + " "
            if (tens <= 4) {
                endIndex = 1
            } else {
                endIndex = 2
            }
        } else if (tens >= 20) {
            var units = tens % 10
            result += bundle.getString(NUM + tens / 10 * 10) + " "
            if (units > 2) {
                result += bundle.getString(NUM + units) + " "
            } else if (units > 0) {
                result += convertUnit(units, type) + " "
            }
            if (units == 1L) {
                endIndex = 0
            } else if (units > 0 && units <= 4) {
                endIndex = 1
            } else {
                endIndex = 2
            }

        } else if (tens > 0) {
            result += convertUnit(tens, type) + " "
            if (tens == 1L) {
                endIndex = 0;
            } else if (tens == 2L) {
                endIndex = 1;
            }
        }
        return result + getName(endIndex, type)
    }

    fun convertLongToWords(value: Long): String {
        if (value == 0L) return bundle.getString(ZERO)
        var type = 0;
        var currentNumber = value
        var result = ""
        var isNegative = value < 0

        if (isNegative) {
            currentNumber *= -1
        }

        while (currentNumber != 0L) {
            var remainder = currentNumber % 1000
            if (remainder > 0) {
                result = convertPartToWords(remainder, type) + " " + result
            }
            type++;
            currentNumber /= 1000
        }
        if (isNegative) {
            result = bundle.getString(MINUS) + " " + result
        }
        return result
    }
}