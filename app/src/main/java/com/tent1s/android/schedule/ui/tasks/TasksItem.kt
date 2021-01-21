package com.tent1s.android.schedule.ui.tasks

sealed class TasksItem {
    class HeaderTask : TasksItem() var header: String? = null

    class ContentTask : TasksItem() {
        var id : Long = 0L
        var title: String? = null
        var inf: String? = null
        var taskDeadlineDay: Int = -1
        var taskDeadlineMount: Int = -1
        var taskDeadlineYear: Int = -1
    }
}