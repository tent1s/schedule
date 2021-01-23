package com.tent1s.android.schedule.ui.tasks.taskslist

import androidx.lifecycle.*
import com.tent1s.android.schedule.database.TasksList
import com.tent1s.android.schedule.ui.tasks.TasksItem
import com.tent1s.android.schedule.ui.timetable.TimetableItem
import kotlinx.coroutines.launch
import java.util.ArrayList

class TasksViewModel() : ViewModel() {


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


    private val _state = MutableLiveData<List<TasksItem>>()
    val state: LiveData<List<TasksItem>> = _state


    fun getTasks(tasks : List<TasksList>) {
        val arrayList = ArrayList<TasksItem>()
        viewModelScope.launch {

            for (j in 0..1) {
                val header = TasksItem.HeaderTask()
                var m = true
                when (j) {
                    0 -> header.header = "Не выполнено"
                    1 -> header.header = "Выполнено"
                }
                when (j) {
                    0 -> m = false
                    1 -> m = true
                }
                val count = getCountTasks(m, tasks)
                var findDay = 0
                if (count != 0) arrayList.add(header)
                var sortAscending = ArrayList<TasksItem.ContentTask>()
                for (i in 0 until count) {
                    val item = TasksItem.ContentTask()

                    for (x in findDay until tasks.size) {
                        val itemDate = tasks[x]
                        if (m == itemDate.isTaskDone) {
                            findDay = x + 1
                            item.title = itemDate.title
                            item.inf = itemDate.information
                            item.taskDeadlineDay = itemDate.deadlineDay
                            item.taskDeadlineMount = itemDate.deadlineMount
                            item.id = itemDate.id
                            item.taskDeadlineYear = itemDate.deadlineYear
                            break
                        }
                    }
                    sortAscending.add(item)
                }
                if (sortAscending.size != 0) {
                    sortAscending = sortTasks(sortAscending)
                }
                for (p in 0 until sortAscending.size){
                    arrayList.add(sortAscending[p])
                }
            }

        }
        _state.value = arrayList
    }
    private fun sortTasks(source: ArrayList<TasksItem.ContentTask>) : ArrayList<TasksItem.ContentTask> {
        return quickSort(source, 0, source.size-1)
    }
    private fun quickSort(source: ArrayList<TasksItem.ContentTask>, leftBorder: Int, rightBorder: Int) : ArrayList<TasksItem.ContentTask>{
        var leftMarker = leftBorder
        var rightMarker = rightBorder
        val pivotDay = source[(leftMarker + rightMarker) / 2].taskDeadlineDay
        val pivotMount = source[(leftMarker + rightMarker) / 2].taskDeadlineMount
        val pivotYear = source[(leftMarker + rightMarker) / 2].taskDeadlineYear
        do {


            while (source[leftMarker].taskDeadlineYear < pivotYear || (source[leftMarker].taskDeadlineMount < pivotMount && source[leftMarker].taskDeadlineYear == pivotYear)
                    || (source[leftMarker].taskDeadlineDay < pivotDay && source[leftMarker].taskDeadlineMount == pivotMount && source[leftMarker].taskDeadlineYear == pivotYear))  {
                leftMarker++
            }


            while (source[rightMarker].taskDeadlineYear > pivotYear || (source[rightMarker].taskDeadlineMount > pivotMount && source[leftMarker].taskDeadlineYear == pivotYear)
                    || (source[leftMarker].taskDeadlineDay > pivotDay && source[leftMarker].taskDeadlineMount == pivotMount && source[leftMarker].taskDeadlineYear == pivotYear)) {
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

    private fun getCountTasks(isTask: Boolean, date: List<TasksList>): Int {
        var count = 0
        for (element in date) {
            if (element.isTaskDone==isTask) count += 1
        }
        return count
    }


}
