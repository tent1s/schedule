package com.tent1s.android.schedule.ui.tasks.taskslist

import androidx.lifecycle.*
import com.tent1s.android.schedule.database.TasksList
import timber.log.Timber
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
                            break
                        }
                    }
                    arrayList.add(item)
                }
            }
        _state.value = arrayList
    }

    private fun getCountTasks(isTask: Boolean, date: List<TasksList>): Int {
        var count = 0
        for (i in 0 until date.size) {
            val item = date[i]
            if (item.isTaskDone==isTask) count += 1
        }
        return count
    }


}
