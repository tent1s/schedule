package com.tent1s.android.schedule.repository

import android.app.Application
import android.os.Parcel
import android.os.Parcelable
import com.tent1s.android.schedule.database.TasksList
import com.tent1s.android.schedule.database.TimetableList
import timber.log.Timber
import java.util.*


class ScheduleRepository {

    fun getAllTimetable(): ArrayList<TimetableList> {
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

    fun getAllTasks(): ArrayList<TasksList> {
        var tasks = ArrayList<TasksList>()

        tasks.add(TasksList(0, "Сделать", "быстро", 1, 0, 0, false))
        tasks.add(TasksList(1, "Задание", "ааааа", 1, 0, 0, true))
        tasks.add(TasksList(2, "Нужно", "выполнить это", 1, 0, 0, false))
        tasks.add(TasksList(3, "Выполнить", "не надо", 1, 0, 0, true))
        tasks.add(TasksList(4, "1234", "1111", 1, 0, 0, true))

        return tasks
    }
}


