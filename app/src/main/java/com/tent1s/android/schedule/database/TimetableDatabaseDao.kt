package com.tent1s.android.schedule.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface TimetableDatabaseDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tasks: List<TimetableList>)


    @Query("SELECT * FROM timetable_table ORDER BY id DESC")
    fun getAllTimetable(): LiveData<List<TimetableList>>

    @Query("DELETE FROM timetable_table")
    suspend fun clear()


}