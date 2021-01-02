package com.tent1s.android.schedule.ui.tasks.newtask

import android.app.DatePickerDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.util.*


class NewTaskViewModel : ViewModel() {

    private val _timePickerDialogData = MutableLiveData<Boolean>()
    val timePickerDialogData: LiveData<Boolean>
        get() = _timePickerDialogData

    private val _navigateToTasks = MutableLiveData<Boolean>()
    val navigateToTasks: LiveData<Boolean>
        get() = _navigateToTasks

    private val _saveTaskInf = MutableLiveData<Boolean>()
    val saveSomeInf: LiveData<Boolean>
        get() = _saveTaskInf


    fun onSaveButtonClick(){
        _saveTaskInf.value = true
    }

    fun onSaveButtonClickComplete(){
        _saveTaskInf.value = false
    }

    fun onDelButtonClick(){
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
}