package com.tent1s.android.schedule.ui.tasks.newtask


import android.app.Application
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.tent1s.android.schedule.database.TasksDatabaseDao
import com.tent1s.android.schedule.database.TasksList
import com.tent1s.android.schedule.utils.convertMonthToString
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime


class NewTaskViewModel(val database: TasksDatabaseDao, application: Application,
                       private val taskId: Long
) : AndroidViewModel(application) {


    private var taskIsExist = false


    private val _isValid = MutableLiveData(false)
    val isValid: LiveData<Boolean>
        get() = _isValid

    private val _titleLive = MutableLiveData<String>()
    val titleLive: LiveData<String>
        get() = _titleLive
    private var title = ""


    private val _aboutLive = MutableLiveData<String>()
    val aboutLive: LiveData<String>
        get() = _aboutLive
    private var about = ""

    private val _date = MutableLiveData("Выбор даты")
    val date: LiveData<String>
        get() = _date


    private val _complete = MutableLiveData( false )
    val complete: LiveData<Boolean>
        get() = _complete


    private var day = -1
    private var month = -1
    private var year = -1

    init {
        if(taskId != -1L){
            viewModelScope.launch {

                val task = get(taskId)
                if (task != null) {
                    day = task.deadlineDay
                    month = task.deadlineMount
                    year = task.deadlineYear
                    _date.value = "$day ${convertMonthToString(month)}"
                    taskIsExist = true
                    _titleLive.postValue(task.title)
                    title = task.title!!
                    _aboutLive.postValue(task.information)
                    about = task.information!!
                    _complete.value = task.isTaskDone
                }
            }
        }
    }



    fun onDelButtonClick(){
        viewModelScope.launch {
            if (taskIsExist){
                del(taskId)
            }
        }
    }




    private fun validation() {
        val isValidTitle = !TextUtils.isEmpty(title)
        val isValidAbout = !TextUtils.isEmpty(about)
        val isValidDayOfWeek = !date.value.equals("Выбор даты")

        _isValid.postValue(isValidTitle && isValidAbout && isValidDayOfWeek)
    }

    fun titleWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                title = charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {
                validation()
            }
        }
    }
    fun aboutWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                about = charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {
                validation()
            }
        }
    }
    fun dateWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                _date.postValue(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {
                validation()
            }
        }
    }

    fun saveIsDoneTask(isChecked: Boolean) {
        _complete.value = isChecked
    }

    fun getDate(date : LocalDateTime){

        val str = date.toString().substring(0, date.toString().lastIndexOf("T"))

        val delimiter = "-"
        val subStr = str.split(delimiter)

        for (i in 0..2) {
            day = subStr[2].toInt()
            month = subStr[1].toInt()
            year = subStr[0].toInt()
        }

    }

     fun saveInf(){

         viewModelScope.launch {
             if (taskIsExist){
                 update(TasksList(taskId, title, about, day, month, year, complete.value))
             }else {
                 insert(TasksList(0, title, about, day, month, year, complete.value))
             }
         }

    }




    private suspend fun insert(task: TasksList) {
        database.insert(task)
    }

    private suspend fun update(task: TasksList) {
        database.update(task)
    }
    private suspend fun get(key: Long): TasksList? {
        return database.get(key)
    }
    private suspend fun del(key: Long) {
        database.del(key)
    }
}