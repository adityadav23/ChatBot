package com.example.covid.chatbot.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object Time {
    fun timeStamp(): String{
        val timestamp = Timestamp(System.currentTimeMillis())
        val simpleDateF = SimpleDateFormat("HH:mm")
        val time = simpleDateF.format(Date(timestamp.time))

        return time.toString()
    }
}