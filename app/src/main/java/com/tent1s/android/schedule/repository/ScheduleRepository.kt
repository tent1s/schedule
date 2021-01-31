package com.tent1s.android.schedule.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.tent1s.android.schedule.database.*

import timber.log.Timber



class ScheduleRepository(application: Application)  {

    private val dataSourceForTasksList = ScheduleDatabase.getInstance(application).tasksDatabaseDao
    val tasks = dataSourceForTasksList.getAllTasks()

    private val dataSourceForTimetableList = ScheduleDatabase.getInstance(application).timetableDatabaseDao
    //val timetable = dataSourceForTimetableList.getAllTimetable()

    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("timetable")




    private val _timetableFirebase = MutableLiveData<List<TimetableList>>()
    val timetableFirebase: LiveData<List<TimetableList>> = _timetableFirebase


    private val _load = MutableLiveData(false)
    val load: LiveData<Boolean> = _load

    fun updateLoad(online : Boolean){
        _load.postValue(online)
    }

    init {

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dateFromFire = ArrayList<TimetableList>()


               for (child in dataSnapshot.children) {
                   val post = child.getValue(TimetableList::class.java)

                   if (post != null) {
                       dateFromFire.add(post)
                   }
               }
                _timetableFirebase.value = dateFromFire
                _load.postValue(true)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                _load.postValue(false)
            }
        }
        database.addValueEventListener(postListener)
    }


}




