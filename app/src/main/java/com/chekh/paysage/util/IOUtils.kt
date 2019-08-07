package com.chekh.paysage.util

import com.chekh.paysage.PaysageApp
import java.io.BufferedReader
import java.io.InputStreamReader

fun readFromAssets(fileName: String): String {
    val builder = StringBuilder()
    val reader = BufferedReader(InputStreamReader(PaysageApp.launcher.assets.open(fileName)))
    var line = reader.readLine()
    while (line != null) {
        builder.append(line)
        line = reader.readLine()
    }
    reader.close()
    return builder.toString()
}