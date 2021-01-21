package com.tent1s.android.schedule.ui.timetable.newtimerow

import android.app.Application
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.tent1s.android.schedule.database.TasksList
import com.tent1s.android.schedule.database.TimetableDatabaseDao
import com.tent1s.android.schedule.database.TimetableList
import kotlinx.coroutines.launch
import timber.log.Timber


class NewTimeRowViewModel(val database: TimetableDatabaseDao, application: Application)
    : AndroidViewModel(application) {

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

    private val _errorNullDate = MutableLiveData<Boolean>()
    val errorNullDate: LiveData<Boolean>
        get() = _errorNullDate

    private val _errorInvalidTime = MutableLiveData<Boolean>()
    val errorInvalidTime: LiveData<Boolean>
        get() = _errorInvalidTime

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
        saveInf()
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
    private fun errorNullDate() {
        _errorNullDate.value = true
    }

    fun errorNullDateComplete(){
        _errorNullDate.value = false
    }
    private fun errorInvalidTime() {
        _errorInvalidTime.value = true
    }

    fun errorInvalidTimeComplete(){
        _errorInvalidTime.value = false
    }


    private val isValid = ObservableBoolean(false)
    val title = ObservableField<String>()
    val about = ObservableField<String>()
    val dayOfWeekString = ObservableField("день недели")
    val startTime = ObservableField("начало")
    val endTime = ObservableField("конец")
    val colorString = ObservableField("выбор цвета")

    private fun validation() {
        val isValidTitle = !TextUtils.isEmpty(title.get())
        val isValidAbout = !TextUtils.isEmpty(about.get())
        val isValidDayOfWeekString = !dayOfWeekString.get().equals("день недели")
        val isValidStartTime = !startTime.get().equals("начало")
        val isValidEndTime = !endTime.get().equals("конец")
        val isValidColor = !colorString.get().equals("выбор цвета")

        isValid.set(isValidTitle && isValidAbout  && isValidDayOfWeekString && isValidStartTime && isValidEndTime && isValidColor)
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
    fun dayOfWeekWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                dayOfWeekString.set(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {
                validation()
            }
        }
    }
    fun startTimeWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                startTime.set(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {
                validation()
            }
        }
    }
    fun endTimeWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                endTime.set(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {
                validation()
            }
        }
    }
    fun colorWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                colorString.set(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {
                validation()
            }
        }
    }


    private fun saveInf(){
        if (isValid.get()){
            var startMinute = -1
            var endMinute = -1
            var startHour = -1
            var endHour = -1
            var color = -1
            var dayOfWeek = -1

            val delimiter = ":"
            var subStr = startTime.get()!!.split(delimiter)
            for (i in 0..1) {
                startMinute = subStr[1].toInt()
                startHour = subStr[0].toInt()
            }
            subStr = endTime.get()!!.split(delimiter)
            for (i in 0..1) {
                endMinute = subStr[1].toInt()
                endHour = subStr[0].toInt()
            }

            dayOfWeek = dayOfWeekToInt()
            color =  colorToInt()

            if ((startHour > endHour) || ((startHour == endHour) && (startMinute > endMinute))){
                errorInvalidTime()
            }else{
                viewModelScope.launch {

                    insert(TimetableList(0, title.get(), about.get(), startHour, startMinute, endHour, endMinute, dayOfWeek, color))
                    Timber.i("День недели: $dayOfWeek Начальное время: $startHour:$startMinute Конечное время: $endHour:$endMinute Заголовок: $title Описание: $about Цвет: $color")
                }
                _saveTimeInf.value = true
            }
        }else{
            errorNullDate()
        }
    }

    private fun dayOfWeekToInt() : Int {
        var dayOfWeekInt = -1
        when (dayOfWeekString.get()) {
            "Понидельник" -> dayOfWeekInt = 0
            "Вторник" -> dayOfWeekInt = 1
            "Среда" -> dayOfWeekInt = 2
            "Четверг" -> dayOfWeekInt = 3
            "Пятница" -> dayOfWeekInt = 4
            "Суббота" -> dayOfWeekInt = 5
        }
        return dayOfWeekInt
    }
    private fun colorToInt() : Int {
        var colorInt = -1
        when (colorString.get()) {
            "Черный" ->  colorInt = 0
            "Синий" -> colorInt = 1
            "Зеленый" -> colorInt = 2
            "Желтый" -> colorInt = 3
            "Красный" -> colorInt = 4
        }
        return colorInt
    }
    private suspend fun insert(timetable: TimetableList) {
        database.insert(timetable)
    }

    private suspend fun update(timetable: TimetableList) {
        database.update(timetable)
    }
    private suspend fun get(key: Long): TimetableList? {
        return database.get(key)
    }
    private suspend fun del(key: Long) {
        database.del(key)
    }
}