package com.tent1s.android.schedule.ui.timetable.timetablelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tent1s.android.schedule.database.TimetableList
import com.tent1s.android.schedule.repository.ScheduleRepository
import java.util.ArrayList

class TimetableViewModel(Repository: ScheduleRepository) : ViewModel() {

    private val repository = Repository
    private val _navigateToSearch = MutableLiveData<Boolean>()
    val navigateToSearch: LiveData<Boolean>
        get() = _navigateToSearch

    fun onFabClicked(){
        _navigateToSearch.value = true
    }

    fun onNavigationToSearch(){
        _navigateToSearch.value = false
    }

    val timetable = getList()

    private val _text = MutableLiveData<String>().apply {
        if (timetable.size == 0) value = "Нажмите “+”, чтобы добавить"
    }
    val text: LiveData<String> = _text



    private fun getList(): ArrayList<ListItem> {
        val arrayList = ArrayList<ListItem>()
        val date = repository.getAllTimetable()

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

            val count = getCount(j, date)
            var findDay = 0
            if (count != 0) arrayList.add(header)
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
        var count = 0
        for (i in 0 until date.size) {
            val item = date[i]
            if (item.timetableDayWeek == dayOfWeek) count += 1
        }
        return count
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