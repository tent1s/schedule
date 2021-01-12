package com.tent1s.android.schedule.ui.tasks.taskslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tent1s.android.schedule.database.TasksList
import com.tent1s.android.schedule.repository.ScheduleRepository
import java.util.ArrayList

class TasksViewModel(Repository: ScheduleRepository) : ViewModel() {
    private  val repository = Repository
    private val _navigateToSearch = MutableLiveData<Boolean>()
    val navigateToSearch: LiveData<Boolean>
        get() = _navigateToSearch

    fun onFabClicked(){
        _navigateToSearch.value = true
    }

    fun onNavigationToSearch(){
        _navigateToSearch.value = false
    }

    val tasks = getTasks()

    private val _text = MutableLiveData<String>().apply {
        if (tasks.size == 0) value = "Нажмите “+”, чтобы добавить"
    }
    val text: LiveData<String> = _text




    @JvmName("getTasks1")
    private fun getTasks(): ArrayList<TasksItem> {
        val arrayList = ArrayList<TasksItem>()
        val date = repository.getAllTasks()

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
            val count = getCountTasks(m, date)
            var findDay = 0
            if (count != 0) arrayList.add(header)
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
        var count = 0
        for (i in 0 until date.size) {
            val item = date[i]
            if (item.isTaskDone==isTask) count += 1
        }
        return count
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