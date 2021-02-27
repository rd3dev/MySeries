package com.github.myseries.util

import java.io.InputStreamReader

object MockResponseFileReader {
    fun readResource(file: String) : String {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(file))
       return reader.readText()
    }
}