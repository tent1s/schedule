package com.tent1s.android.schedule.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.tent1s.android.schedule.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import timber.log.Timber
import kotlin.coroutines.coroutineContext


class ScheduleRepository(application: Application)  {

    private val dataSourceForTasksList = ScheduleDatabase.getInstance(application).tasksDatabaseDao
    val tasks = dataSourceForTasksList.getAllTasks()

    private val dataSourceForTimetableList = ScheduleDatabase.getInstance(application).timetableDatabaseDao
    val timetable = dataSourceForTimetableList.getAllTimetable()

    private val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference("timetable")


    private val _load = MutableLiveData(false)
    val load: LiveData<Boolean> = _load



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

                GlobalScope.launch {
                    refreshVideos(dateFromFire)
                }

                _load.postValue(true)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                _load.postValue(false)
            }
        }
        firebase.addValueEventListener(postListener)
    }


    suspend fun refreshVideos(dateFromFire : List<TimetableList>) {
        withContext(Dispatchers.IO) {
            dataSourceForTimetableList.clear()
            dataSourceForTimetableList.insertAll(dateFromFire)
        }
    }


}




