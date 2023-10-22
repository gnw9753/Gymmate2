package com.example.gymmate.data

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.InputStream

class CSVReader (private val inputStream: InputStream){
    fun read(): List<List<String>> {
        return csvReader().readAll(inputStream)
    }
}