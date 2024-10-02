package com.example.todo
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
object Formater {
    fun  Date.dateFormat(): String {
        val simpleDate = SimpleDateFormat("dd /mm /yyyy", Locale.getDefault())
        return simpleDate.format(this)

    }

    fun Date.timeFormat():String{
        val simpleTimeFormat=SimpleDateFormat("hh :mm",Locale.getDefault())
            return simpleTimeFormat.format(this)
    }



}