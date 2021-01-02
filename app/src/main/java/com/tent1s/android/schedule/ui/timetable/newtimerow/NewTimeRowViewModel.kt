package com.tent1s.android.schedule.ui.timetable.newtimerow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewTimeRowViewModel() : ViewModel() {
    private val _dayOfWeekButton = MutableLiveData<Boolean>()
    val dayOfWeekButton: LiveData<Boolean>
        get() = _dayOfWeekButton

    private val _timetableStartTime = MutableLiveData<Boolean>()
    val timetableStartTime: LiveData<Boolean>
        get() = _timetableStartTime

    private val _timetableEndTime = MutableLiveData<Boolean>()
    val timetableEndTime: LiveData<Boolean>
        get() = _timetableEndTime

    private val _saveTimeInf = MutableLiveData<Boolean>()
    val saveTimeInf: LiveData<Boolean>
        get() = _saveTimeInf

    private val _colorButton = MutableLiveData<Boolean>()
    val colorButton: LiveData<Boolean>
        get() = _colorButton

    fun onDayOfWeekButtonClick() {
        _dayOfWeekButton.value = true
    }

    fun onDayOfWeekButtonClickComplete(){
        _dayOfWeekButton.value = false
    }

    fun onTimetableStartTimeButtonClick() {
        _timetableStartTime.value = true
    }

    fun onTimetableStartTimeButtonClickComplete(){
        _timetableStartTime.value = false
    }
    fun onTimetableEndTimeButtonClick() {
        _timetableEndTime.value = true
    }

    fun onTimetableEndTimeButtonClickComplete(){
        _timetableEndTime.value = false
    }
    fun onSaveTimeInfButtonClick() {
        _saveTimeInf.value = true
    }

    fun onSaveTimeInfButtonClickComplete(){
        _saveTimeInf.value = false
    }
    fun onColorButtonClick() {
        _colorButton.value = true
    }

    fun onColorButtonClickComplete(){
        _colorButton.value = false
    }
}