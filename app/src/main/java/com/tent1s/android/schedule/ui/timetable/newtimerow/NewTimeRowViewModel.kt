package com.tent1s.android.schedule.ui.timetable.newtimerow

import android.app.Application
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.tent1s.android.schedule.database.TimetableList
import com.tent1s.android.schedule.utils.SingleLiveEvent
import com.tent1s.android.schedule.utils.timetableStartTimeToString
import kotlinx.coroutines.launch
import timber.log.Timber


class NewTimeRowViewModel( application: Application,
                          private val timetableId : String, private val weekId :Int, private val firebase: DatabaseReference) : AndroidViewModel(application) {

    private var timetableIsExist = false


    private val _isValid = MutableLiveData(false)
    val isValid: LiveData<Boolean>
        get() = _isValid


    private val _titleDatabase = MutableLiveData<String>()
    val titleDatabase: LiveData<String>
        get() = _titleDatabase

    private val _title = MutableLiveData("")
    val title: LiveData<String>
        get() = _title


    private val _aboutLive = MutableLiveData<String>()
    val aboutLive: LiveData<String>
        get() = _aboutLive

    private val _about = MutableLiveData("")
    val about: LiveData<String>
        get() = _about


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

    private val _timetable = MutableLiveData<TimetableList>()
    val timetable: LiveData<TimetableList>
        get() = _timetable

    init {
        if(!TextUtils.isEmpty(timetableId)){
            viewModelScope.launch {


                val postListener = object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        for (child in dataSnapshot.children) {

                            if(child.key == timetableId){

                                _timetable.postValue(child.getValue(TimetableList::class.java))

                            }
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {


                        Timber.i("Error load")

                        }
                    }
                    firebase.addListenerForSingleValueEvent(postListener)
            }
        }
    }

    fun setVal(timetable :TimetableList ){

            timetableIsExist = true
            _title.value = timetable.title!!
            _titleDatabase.value = timetable.title!!
            _aboutLive.value = timetable.information!!
            _about.value = timetable.information!!
            _dayOfWeekString.value = dayOfWeekIntToString(timetable.dayWeek)
            _colorString.value = colorIntToString(timetable.colorId)
            _startTime.value = timetableStartTimeToString(timetable.StartTimeHour, timetable.StartTimeMinute)
            _endTime.value = timetableStartTimeToString(timetable.EndTimeHour,timetable.EndTimeMinute)

    }





    private val errorInvalidTime = SingleLiveEvent<Boolean>()
    fun getErrorInvalidTime(): SingleLiveEvent<Boolean> {
        return errorInvalidTime
    }



    fun delButtonOnclick() {
        viewModelScope.launch {
            if (timetableIsExist){
                firebase.child(timetableId).removeValue()
            }
        }
    }



    private fun validation() {
        val isValidTitle = !TextUtils.isEmpty(title.value)
        val isValidAbout = !TextUtils.isEmpty(about.value)
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
                _title.postValue(charSequence.toString())
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
                _about.postValue(charSequence.toString())
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
                errorInvalidTime.postValue(true)
            }else{
                errorInvalidTime.postValue(false)
                viewModelScope.launch {
                    if (timetableIsExist) {

                        firebase.child(timetableId).setValue(TimetableList(timetableId, title.value, about.value, startHour, startMinute, endHour, endMinute, dayOfWeek, color, weekId))
                    }else {
                        val key = firebase.child("posts").push().key
                        firebase.child(key!!).setValue(TimetableList(key, title.value, about.value, startHour, startMinute, endHour, endMinute, dayOfWeek, color, weekId))
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
                "Воскресенье" -> 6
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
            6 -> "Воскресенье"
            else -> "день недели"
        }
    }

}


