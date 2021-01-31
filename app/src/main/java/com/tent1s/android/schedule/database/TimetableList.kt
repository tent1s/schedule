package com.tent1s.android.schedule.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.security.auth.Subject

@Entity(tableName = "timetable_table")
data class TimetableList(
        @PrimaryKey(autoGenerate = false)
        var id: String = "",

        @ColumnInfo(name = "title")
        var title:  String? = null,

        @ColumnInfo(name = "information")
        var information: String? = null,

        @ColumnInfo(name = "start_time_hour")
        var StartTimeHour: Int = -1,

        @ColumnInfo(name = "start_time_minute")
        var StartTimeMinute: Int = -1,

        @ColumnInfo(name = "end_hour")
        var EndTimeHour: Int = -1,

        @ColumnInfo(name = "end_minute")
        var EndTimeMinute: Int = -1,

        @ColumnInfo(name = "day_week")
        var dayWeek: Int = -1,

        @ColumnInfo(name = "color_id")
        var colorId: Int = -1,

        @ColumnInfo(name = "week_id")
        var weekId: Int = -1

)
