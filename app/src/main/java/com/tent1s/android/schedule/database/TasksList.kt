package com.tent1s.android.schedule.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_list_table")
data class TasksList(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,

        @ColumnInfo(name = "task_title")
        var title: String? = null,

        @ColumnInfo(name = "task_information")
        var information: String? = null,

        @ColumnInfo(name = "task_deadline_day")
        var deadlineDay: Int = 0,

        @ColumnInfo(name = "task_deadline_mount")
        var deadlineMount: Int = 0,

        @ColumnInfo(name = "task_deadline_year")
        var deadlineYear: Int = 0,

        @ColumnInfo(name = "is_task_done")
        var isTaskDone: Boolean? = false
)
