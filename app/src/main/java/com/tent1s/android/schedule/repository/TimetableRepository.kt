package com.tent1s.android.schedule.repository

import android.app.Application
import com.tent1s.android.schedule.database.TimetableList
import timber.log.Timber
import java.util.ArrayList

class TimetableRepository : Application()  {

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
                val header = Header()

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
                    val item = ContentItem()

                    for (x in findDay until date.size) {
                        val itemDate = date[x]
                        if (itemDate.timetableDayWeek == j) {
                            findDay = x + 1
                            item.title = itemDate.timetableTitle
                            item.inf = itemDate.timetableInformation
                            break
                        }
                    }

                    arrayList.add(item)
                }
            }
            return arrayList
        }



        fun getCount(dayOfWeek: Int, date: ArrayList<TimetableList>): Int {
            var count: Int = 0
            for (i in 0 until date.size) {
                val item = date[i]
                if (item.timetableDayWeek == dayOfWeek) count += 1
            }
            return count
        }



        private fun getAllTimetable(): ArrayList<TimetableList> {
            var timetable = ArrayList<TimetableList>()

            timetable.add(TimetableList(0,"Матем", "Лекция", 1))
            timetable.add(TimetableList(1,"Матем", "Практика", 0))
            timetable.add(TimetableList(2,"Схемотехника", "Лекция", 1))
            timetable.add(TimetableList(3,"Схемотехника", "Лабораторные", 2))
            timetable.add(TimetableList(4,"Электротехника", "Лекция", 3))
            timetable.add(TimetableList(5,"Электротехника", "Лабораторные", 2))
            timetable.add(TimetableList(6,"Криптография", "Лабораторные", 4))
            timetable.add(TimetableList(7,"Криптография", "Практика", 4))
            timetable.add(TimetableList(8,"Криптография", "Лекция", 5))
            timetable.add(TimetableList(9,"ОС", "Практика", 4))
            timetable.add(TimetableList(10,"ОС", "Лекция", 4))
            timetable.add(TimetableList(11,"Тервер", "Практика", 0))
            timetable.add(TimetableList(12,"Тервер", "Лекция", 0))

            return timetable
        }
    }





    class Header : ListItem() {
        var header: String? = null
    }
    class ContentItem : ListItem() {
        var title: String? = null
        var inf: String? = null
    }

    open class ListItem {
    }

}

