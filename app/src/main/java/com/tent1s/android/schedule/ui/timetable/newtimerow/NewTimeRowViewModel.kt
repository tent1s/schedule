package com.tent1s.android.schedule.ui.timetable.newtimerow

import android.app.Application
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.tent1s.android.schedule.database.TimetableDatabaseDao
import com.tent1s.android.schedule.database.TimetableList
import com.tent1s.android.schedule.utils.timetableStartTimeToString
import kotlinx.coroutines.launch
import timber.log.Timber


class NewTimeRowViewModel(val database: TimetableDatabaseDao, application: Application,
                          private val timetableId : Long, private val weekId :Int) : AndroidViewModel(application) {

    private var timetableIsExist = false


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


    private val _dayOfWeekString = MutableLiveData("день недели")
    val dayOfWeekString: LiveData<String>
        get() = _dayOfWeekString


    private val _startTime = MutableLiveData("начало")
    val startTime: LiveData<String>
        get() = _startTime


    private val _endTime = MutableLiveData("конец")
    val endTime: LiveData<String>
        get() = _endTime


    private val _colorString = MutableLiveData("выбор цвета")
    val colorString: LiveData<String>
        get() = _colorString

    init {
        if(timetableId != -1L){
            viewModelScope.launch {

                val timetable = get(timetableId)
                if (timetable != null) {
                    timetableIsExist = true
                    title = timetable.title!!
                    _titleLive.value = timetable.title!!
                    _aboutLive.value = timetable.information!!
                    about = timetable.information!!
                    _dayOfWeekString.value = dayOfWeekIntToString(timetable.dayWeek)
                    _colorString.value = colorIntToString(timetable.colorId)
                    _startTime.value = timetableStartTimeToString(timetable.StartTimeHour, timetable.StartTimeMinute)
                    _endTime.value = timetableStartTimeToString(timetable.EndTimeHour,timetable.EndTimeMinute)
                }
            }
        }
    }


    private val _errorInvalidTime = MutableLiveData<Boolean>()
    val errorInvalidTime: LiveData<Boolean>
        get() = _errorInvalidTime


    private fun errorInvalidTime() {
        _errorInvalidTime.value = true
    }

    fun errorInvalidTimeComplete(){
        _errorInvalidTime.value = false
    }


    fun delButtonOnclick() {
        viewModelScope.launch {
            if (timetableIsExist){
                del(timetableId)
            }
        }
    }



    private fun validation() {
        val isValidTitle = !TextUtils.isEmpty(title)
        val isValidAbout = !TextUtils.isEmpty(about)
        val isValidDayOfWeekString = !dayOfWeekString.value.equals("день недели")
        val isValidStartTime = !startTime.value.equals("начало")
        val isValidEndTime = !endTime.value.equals("конец")
        val isValidColor = !colorString.value.equals("выбор цвета")

        _isValid.postValue(isValidTitle && isValidAbout  && isValidDayOfWeekString && isValidStartTime && isValidEndTime && isValidColor)
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
    fun dayOfWeekWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                _dayOfWeekString.postValue(charSequence.toString())
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
                _startTime.postValue(charSequence.toString())
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
                _endTime.postValue(charSequence.toString())
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
                _colorString.postValue(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {
                validation()
            }
        }
    }


    fun saveInf(){
            var startMinute = -1
            var endMinute = -1
            var startHour = -1
            var endHour = -1
            var dayOfWeek = -1

            val delimiter = ":"
            var subStr = startTime.value!!.split(delimiter)
            for (i in 0..1) {
                startMinute = subStr[1].toInt()
                startHour = subStr[0].toInt()
            }
            subStr = endTime.value!!.split(delimiter)
            for (i in 0..1) {
                endMinute = subStr[1].toInt()
                endHour = subStr[0].toInt()
            }
            Timber.i("12321 ${dayOfWeek}, ${dayOfWeekString.value}")
            dayOfWeek = dayOfWeekToInt()
            val color : Int = colorToInt()

            if ((startHour > endHour) || ((startHour == endHour) && (startMinute > endMinute))){
                errorInvalidTime()
            }else{
                viewModelScope.launch {
                    if (timetableIsExist) {
                        update(TimetableList(timetableId, title, about, startHour, startMinute, endHour, endMinute, dayOfWeek, color, weekId))
                    }else {
                        insert(TimetableList(0, title, about, startHour, startMinute, endHour, endMinute, dayOfWeek, color, weekId))
                    }
                }
            }
    }

    private fun dayOfWeekToInt() : Int {
        return try {
            when (dayOfWeekString.value) {
                "Понидельник" -> 0
                "Вторник" -> 1
                "Среда" -> 2
                "Четверг" -> 3
                "Пятница" -> 4
                "Суббота" -> 5
                else -> -1
            }
        }catch (e: NumberFormatException) { -1 }
    }
    private fun colorToInt() : Int {
        var colorInt = -1
        when (colorString.value) {
            "Черный" ->  colorInt = 0
            "Синий" -> colorInt = 1
            "Зеленый" -> colorInt = 2
            "Желтый" -> colorInt = 3
            "Красный" -> colorInt = 4
        }
        return colorInt
    }
    fun colorIntToString(which: Int) : String {
        return when (which) {
            0 -> "Черный"
            1 -> "Синий"
            2 -> "Зеленый"
            3 -> "Желтый"
            4 -> "Красный"
            else -> "выбор цвета"
        }
    }
    fun dayOfWeekIntToString(which: Int) : String {
        return when (which) {
            0 ->  "Понидельник"
            1 ->  "Вторник"
            2 ->  "Среда"
            3 ->  "Четверг"
            4 ->  "Пятница"
            5 ->  "Суббота"
            else -> "день недели"
        }
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