package com.tent1s.android.schedule.ui.timetable.timetablelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tent1s.android.schedule.database.TimetableList
import com.tent1s.android.schedule.ui.timetable.TimetableItem
import java.util.ArrayList


class TimetableViewModel() : ViewModel() {


    private val _navigateToSearch = MutableLiveData<Boolean>()
    val navigateToSearch: LiveData<Boolean>
        get() = _navigateToSearch

    fun onFabClicked(){
        _navigateToSearch.value = true
    }

    fun onNavigationToSearch(){
        _navigateToSearch.value = false
    }

    private val _checkEmptyList = MutableLiveData<Boolean>()
    val checkEmptyList: LiveData<Boolean>
        get() = _checkEmptyList

    fun listIsEmpty(){
        _checkEmptyList.value = true
    }
    fun listIsNotEmpty(){
        _checkEmptyList.value = false
    }


    private val _state = MutableLiveData<List<TimetableItem>>()
    val state: LiveData<List<TimetableItem>> = _state


     fun getTimetable(date : List<TimetableList>) {
        val arrayList = ArrayList<TimetableItem>()

        for (j in 0..5) {
            val header = TimetableItem.Header()

            when (j) {
                0 -> header.header = "Понидельник"
                1 -> header.header = "Вторник"
                2 -> header.header = "Среда"
                3 -> header.header = "Четрверг"
                4 -> header.header = "Пятница"
                5 -> header.header = "Суббота"
            }

            val count = getCount(j, date)
            var findDay = 0
            if (count != 0) arrayList.add(header)
            for (i in 0 until count) {
                val item = TimetableItem.ContentItem()

                for (x in findDay until date.size) {
                    val itemDate = date[x]
                    if (itemDate.dayWeek == j) {
                        findDay = x + 1
                        item.title = itemDate.title
                        item.inf = itemDate.information
                        item.colorId = itemDate.colorId
                        item.endTimeHour = itemDate.EndTimeHour
                        item.endTimeMinute = itemDate.EndTimeMinute
                        item.startTimeHour = itemDate.StartTimeHour
                        item.startTimeMinute = itemDate.StartTimeMinute
                        break
                    }
                }

                arrayList.add(item)
            }
        }
        _state.value = arrayList
    }


    private fun getCount(dayOfWeek: Int, date : List<TimetableList>): Int {
        var count = 0
        for (i in 0 until date.size) {
            val item = date[i]
            if (item.dayWeek == dayOfWeek) count += 1
        }
        return count
    }
}

