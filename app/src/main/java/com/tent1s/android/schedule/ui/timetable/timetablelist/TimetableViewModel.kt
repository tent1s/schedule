package com.tent1s.android.schedule.ui.timetable.timetablelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tent1s.android.schedule.database.TimetableList
import com.tent1s.android.schedule.ui.timetable.TimetableItem
import kotlinx.coroutines.launch
import java.util.*


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


     fun getTimetable(date: List<TimetableList>) {
        val arrayList = ArrayList<TimetableItem>()
         viewModelScope.launch {

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
                 var sortAscending = ArrayList<TimetableItem.ContentItem>()
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
                             item.id = itemDate.id
                             break
                         }
                     }

                     sortAscending.add(item)
                 }
                 if (sortAscending.size != 0) {
                     sortAscending = sortTimetable(sortAscending)
                 }
                 for (p in 0 until sortAscending.size){
                     arrayList.add(sortAscending[p])
                 }
             }

         }
        _state.value = arrayList
    }


    private fun sortTimetable(source: ArrayList<TimetableItem.ContentItem>) : ArrayList<TimetableItem.ContentItem> {
        return quickSort(source, 0, source.size-1)
    }

    private fun quickSort(source: ArrayList<TimetableItem.ContentItem>, leftBorder: Int, rightBorder: Int) : ArrayList<TimetableItem.ContentItem>{
        var leftMarker = leftBorder
        var rightMarker = rightBorder
        val pivot = source[(leftMarker + rightMarker) / 2].startTimeHour
        val pivotMinute = source[(leftMarker + rightMarker) / 2].startTimeMinute
        do {


            while (source[leftMarker].startTimeHour < pivot || (source[leftMarker].startTimeMinute < pivotMinute && source[leftMarker].startTimeHour == pivot)) {
                leftMarker++
            }


            while (source[rightMarker].startTimeHour > pivot || (source[rightMarker].startTimeMinute > pivotMinute && source[leftMarker].startTimeHour == pivot)) {
                rightMarker--
            }


            if (leftMarker <= rightMarker) {


                if (leftMarker < rightMarker) {
                    val tmp = source[leftMarker]
                    source[leftMarker] = source[rightMarker]
                    source[rightMarker] = tmp
                }


                leftMarker++
                rightMarker--
            }
        } while (leftMarker <= rightMarker)



        if (leftMarker < rightBorder) {
            quickSort(source, leftMarker, rightBorder)
        }
        if (leftBorder < rightMarker) {
            quickSort(source, leftBorder, rightMarker)
        }
        return source
    }



    private fun getCount(dayOfWeek: Int, date: List<TimetableList>): Int {
        var count = 0
        for (element in date) {
            if (element.dayWeek == dayOfWeek) count += 1
        }
        return count
    }

}

