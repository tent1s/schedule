package com.tent1s.android.schedule.utils

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


fun Context.shortToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun convertMonthToString(mount: Int) : String{
    return when(mount){
        1 -> "Января"
        2 -> "Февраля"
        3 -> "Марта"
        4 -> "Апреля"
        5 -> "Мая"
        6 -> "Июня"
        7 -> "Июля"
        8 -> "Августа"
        9 -> "Сентября"
        10 -> "Октября"
        11 -> "Ноября"
        12 -> "Декабря"
        else -> "Error"
    }
}

fun timetableTimeToString(startHour :Int, startMinute :Int, endHour :Int, endMinute :Int) : String {
    var str  = ""
    str += if (startHour < 10){
        "0$startHour"
    }else{
        startHour.toString()
    }
    str += if (startMinute < 10){
        ":0$startMinute"
    }else{
        ":$startMinute"
    }
    str += if (endHour < 10){
        "-0$endHour"
    }else{
        "-$endHour"
    }
    str += if (endMinute < 10){
        ":0$endMinute"
    }else{
        ":$endMinute"
    }
    return str
}