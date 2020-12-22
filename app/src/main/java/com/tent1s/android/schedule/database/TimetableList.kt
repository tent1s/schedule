package com.tent1s.android.schedule.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.security.auth.Subject

@Entity(tableName = "timetable_table")
data class TimetableList(
    @PrimaryKey(autoGenerate = true)
    var timetableId: Long = 0L,

    @ColumnInfo(name = "task_title")
    var timetableTitle:  String? = null,

    @ColumnInfo(name = "task_information")
    var timetableInformation: String? = null,

//    @ColumnInfo(name = "timetable_start_time")
//    var timetableStartTime: Long = 2,
//
//    @ColumnInfo(name = "timetable_end_time")
//    var timetableEndTime: Long =??,

    @ColumnInfo(name = "timetable_day_week")
    var timetableDayWeek: Int = -1,

    @ColumnInfo(name = "timetable_color_id")
    var timetableColorId: Int = -1
)
