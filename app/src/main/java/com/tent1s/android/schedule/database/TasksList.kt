package com.tent1s.android.schedule.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_list_table")
data class TasksList(
        @PrimaryKey(autoGenerate = true)
        var taskId: Long = 0L,

        @ColumnInfo(name = "task_title")
        var taskTitle: String? = null,

        @ColumnInfo(name = "task_information")
        var taskInformation: String? = null,

        @ColumnInfo(name = "task_deadline_day")
        var taskDeadlineDay: Int = 0,

        @ColumnInfo(name = "task_deadline_mount")
        var taskDeadlineMount: Int = 0,

        @ColumnInfo(name = "task_deadline_year")
        var taskDeadlineYear: Int = 0,

        @ColumnInfo(name = "is_task_done")
        var isTaskDone: Boolean = false
)
