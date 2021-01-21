package com.tent1s.android.schedule.ui.tasks.newtask


import android.app.Application
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.tent1s.android.schedule.database.TasksDatabaseDao
import com.tent1s.android.schedule.database.TasksList
import com.tent1s.android.schedule.utils.convertMonthToString
import kotlinx.coroutines.launch


class NewTaskViewModel(val database: TasksDatabaseDao, application: Application,
                       private val taskId: Long
) : AndroidViewModel(application) {


    private var taskIsExist = false

    private val isValid = ObservableBoolean(false)
    val title = ObservableField<String>()
    val about = ObservableField<String>()
    val date = ObservableField("Выбор даты")
    val complete = ObservableBoolean(false)

    private var day = -1
    private var month = -1
    private var year = -1

    init {
        if(taskId != -1L){
            viewModelScope.launch {

                val task = get(taskId)
                if (task != null) {
                    taskIsExist = true
                    title.set(task.title)
                    about.set(task.information)
                    day = task.deadlineDay
                    month = task.deadlineMount
                    year = task.deadlineYear
                    task.isTaskDone?.let { complete.set(it) }
                    date.set("$day ${convertMonthToString(month)}")
                }
            }
        }
    }

    private val _timePickerDialogData = MutableLiveData<Boolean>()
    val timePickerDialogData: LiveData<Boolean>
        get() = _timePickerDialogData

    private val _navigateToTasks = MutableLiveData<Boolean>()
    val navigateToTasks: LiveData<Boolean>
        get() = _navigateToTasks

    private val _saveTaskInf = MutableLiveData<Boolean>()
    val saveSomeInf: LiveData<Boolean>
        get() = _saveTaskInf

    private val _errorToast = MutableLiveData<Boolean>()
    val errorToast: LiveData<Boolean>
        get() = _errorToast


    fun onSaveButtonClick(){
        saveInf()
    }

    fun onSaveButtonClickComplete(){
        _saveTaskInf.value = false
    }

    fun onDelButtonClick(){
        viewModelScope.launch {
            if (taskIsExist){
                del(taskId)
            }
        }
        _navigateToTasks.value = true
    }

    fun onNavigateToTasks(){
        _navigateToTasks.value = false
    }

    fun onDisplayTimePickerDialogClick() {
        _timePickerDialogData.value = true
    }

    fun getTimePickerDialogData(){
        _timePickerDialogData.value = false
    }
    private fun errorStart() {
        _errorToast.value = true
    }

    fun errorEnd(){
        _errorToast.value = false
    }



    private fun validation() {
        val isValidTitle = !TextUtils.isEmpty(title.get())
        val isValidAbout = !TextUtils.isEmpty(about.get())
        val isValidDayOfWeek = !date.get().equals("Выбор даты")

        isValid.set(isValidTitle && isValidAbout && isValidDayOfWeek)
    }

    fun titleWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                title.set(charSequence.toString())
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
                about.set(charSequence.toString())
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
                date.set(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {
                validation()
            }
        }
    }
    fun saveGender(isChecked: Boolean) {
        complete.set(isChecked)
    }
    fun getDate(newDay:Int, newMonth:Int, newYear: Int ){
        day = newDay
        month = newMonth
        year = newYear
    }

    private fun saveInf(){
        if (isValid.get()){

            viewModelScope.launch {
                if (taskIsExist){
                    update(TasksList(taskId, title.get(), about.get(), day, month, year, complete.get()))
                }else {
                    insert(TasksList(0, title.get(), about.get(), day, month, year, complete.get()))
                }
            }
            _saveTaskInf.value = true
        }else{
            errorStart()
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