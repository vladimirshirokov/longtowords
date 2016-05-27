package ru.livemaster.longtowords

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter
import java.util.*

val APP_STRINGS_RES = "app_strings"
val OPEN_FILE_ERROR = "open_file_error"
val ENTER_INPUT_FILE = "enter_input_file"
val NOT_NUMBER = "not_number"
val OUT_OF_RANGE = "out_of_range"
val OUT_FILE = "out.txt"
val MAX_VALUE = 999999999999999999
val MIN_VALUE = -MAX_VALUE

private fun readFile(fileName: String): ArrayList<String> {
    val records = ArrayList<String>()
    FileReader(fileName).use {
        val reader: BufferedReader = BufferedReader(it)
        var line = reader.readLine()
        while (line != null) {
            records.add(line);
            line = reader.readLine()
        }
    }
    return records
}

private fun isNumeric(str: String): Boolean {
    return str.matches(Regex("[+-]?\\d*(\\d+)?"));
}

fun main(args: Array<String>) {
    val bundle = ResourceBundle.getBundle(APP_STRINGS_RES)
    val openFileError = bundle.getString(OPEN_FILE_ERROR)
    val enterNewInputFile = bundle.getString(ENTER_INPUT_FILE)
    val notNumber = bundle.getString(NOT_NUMBER)
    val outOfRange = bundle.getString(OUT_OF_RANGE)

    var longToWords = LongToWords()
    var fileName: String

    if (args.size > 0) {
        fileName = args[0]
    } else {
        println(enterNewInputFile);
        fileName = System.console().readLine();
    }
    try {
        FileWriter(OUT_FILE).use {
            val writer = BufferedWriter(it)
            val records = readFile(fileName)
            for (value in records) {
                if (isNumeric(value)) {
                    try {
                        val number = value.toLong()
                        if (number >= MIN_VALUE && number <= MAX_VALUE) {
                            writer.write(longToWords.convertLongToWords(value.toLong()))
                        } else {
                            writer.write(outOfRange)
                        }
                    } catch (e: NumberFormatException) {
                        writer.write(outOfRange)
                    }
                } else {
                    println(notNumber)
                    writer.write(notNumber)
                }
                writer.newLine()
            }
            writer.flush()
        }
    } catch(e: Exception) {
        println(openFileError)
    }
}
