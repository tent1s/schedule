package com.tent1s.android.schedule.repository

import android.app.Application
import android.os.Parcel
import android.os.Parcelable
import com.tent1s.android.schedule.database.TasksList
import com.tent1s.android.schedule.database.TimetableList
import timber.log.Timber
import java.util.*


class ScheduleRepository : Application()  {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        getList()
    }


    companion object {
        fun getList(): ArrayList<ListItem> {
            val arrayList = ArrayList<ListItem>()
            val date = getAllTimetable()

            for (j in 0..5) {
                val header = ListItem.Header()

                when (j) {
                    0 -> header.header = "Понидельник"
                    1 -> header.header = "Вторник"
                    2 -> header.header = "Среда"
                    3 -> header.header = "Четрверг"
                    4 -> header.header = "Пятница"
                    5 -> header.header = "Суббота"
                }

                arrayList.add(header)
                val count = getCount(j, date)
                var findDay = 0
                for (i in 0 until count) {
                    val item = ListItem.ContentItem()

                    for (x in findDay until date.size) {
                        val itemDate = date[x]
                        if (itemDate.timetableDayWeek == j) {
                            findDay = x + 1
                            item.title = itemDate.timetableTitle
                            item.inf = itemDate.timetableInformation
                            item.colorId = itemDate.timetableColorId
                            break
                        }
                    }

                    arrayList.add(item)
                }
            }
            return arrayList
        }



        private fun getCount(dayOfWeek: Int, date: ArrayList<TimetableList>): Int {
            var count: Int = 0
            for (i in 0 until date.size) {
                val item = date[i]
                if (item.timetableDayWeek == dayOfWeek) count += 1
            }
            return count
        }



        private fun getAllTimetable(): ArrayList<TimetableList> {
            var timetable = ArrayList<TimetableList>()

            timetable.add(TimetableList(0, "Матем", "Лекция", 1, 0))
            timetable.add(TimetableList(1, "Матем", "Практика", 0, 0))
            timetable.add(TimetableList(2, "Схемотехника", "Лекция", 1, 1))
            timetable.add(TimetableList(3, "Схемотехника", "Лабораторные", 2, 1))
            timetable.add(TimetableList(4, "Электротехника", "Лекция", 3, 1))
            timetable.add(TimetableList(5, "Электротехника", "Лабораторные", 2, 1))
            timetable.add(TimetableList(6, "Криптография", "Лабораторные", 4, 2))
            timetable.add(TimetableList(7, "Криптография", "Практика", 4, 2))
            timetable.add(TimetableList(8, "Криптография", "Лекция", 5, 2))
            timetable.add(TimetableList(9, "ОС", "Практика", 4, 3))
            timetable.add(TimetableList(10, "ОС", "Лекция", 4, 3))
            timetable.add(TimetableList(11, "Тервер", "Практика", 0, 4))
            timetable.add(TimetableList(12, "Тервер", "Лекция", 0, 4))

            return timetable
        }
        private fun getAllTasks(): ArrayList<TasksList> {
            var tasks = ArrayList<TasksList>()

            tasks.add(TasksList(0, "Сделать", "быстро", 1, 0, 0, false))
            tasks.add(TasksList(1, "Задание", "ааааа", 1, 0, 0, true))
            tasks.add(TasksList(2, "Нужно", "выполнить это", 1, 0, 0, false))
            tasks.add(TasksList(3, "Выполнить", "не надо", 1, 0, 0, true))
            tasks.add(TasksList(4, "1234", "1111", 1, 0, 0, true))

            return tasks
        }
        fun getTasks(): ArrayList<TasksItem> {
            val arrayList = ArrayList<TasksItem>()
            val date = getAllTasks()

            for (j in 0..1) {
                val header = TasksItem.HeaderTask()
                var m = true
                when (j) {
                    0 -> header.header = "Выполнено"
                    1 -> header.header = "Не выполнено"
                }
                when (j) {
                    0 -> m = true
                    1 -> m = false
                }
                arrayList.add(header)
                val count = getCountTasks(m, date)
                var findDay = 0
                for (i in 0 until count) {
                    val item = TasksItem.ContentTask()

                    for (x in findDay until date.size) {
                        val itemDate = date[x]
                        if (m==itemDate.isTaskDone) {
                            findDay = x + 1
                            item.title = itemDate.taskTitle
                            item.inf = itemDate.taskInformation
                            break
                        }
                    }
                    arrayList.add(item)
                }
            }
            return arrayList
        }

        private fun getCountTasks(isTask: Boolean, date: ArrayList<TasksList>): Int {
            var count: Int = 0
            for (i in 0 until date.size) {
                val item = date[i]
                if (item.isTaskDone==isTask) count += 1
            }
            return count
        }
    }
}

sealed class ListItem {
    class Header : ListItem() var header: String? = null

    class ContentItem : ListItem() {
        var title: String? = null
        var inf: String? = null
        var colorId: Int? = null
    }
}

sealed class TasksItem {
    class HeaderTask : TasksItem() var header: String? = null

    class ContentTask : TasksItem() {
        var title: String? = null
        var inf: String? = null
        var taskDeadlineDay: Int = -1
        var taskDeadlineMount: Int = -1
        var taskDeadlineYear: Int = -1
    }
}