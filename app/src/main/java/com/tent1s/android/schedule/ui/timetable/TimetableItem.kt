package com.tent1s.android.schedule.ui.timetable

import androidx.room.ColumnInfo

sealed class TimetableItem {
    class Header : TimetableItem() var header: String? = null

    class ContentItem : TimetableItem() {
        var id : Long = -1
        var title: String? = null
        var inf: String? = null
        var colorId: Int? = null
        var startTimeHour: Int = -1
        var startTimeMinute: Int = -1
        var endTimeHour: Int = -1
        var endTimeMinute: Int = -1
        var weekId: Int = -1
    }
}