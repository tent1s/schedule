package com.tent1s.android.schedule.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TimetableDatabaseDao {

    @Insert
    suspend fun insert(task: TimetableList)


    @Update
    suspend fun update(task: TimetableList)


    @Query("SELECT * from timetable_table WHERE id = :key")
    suspend fun get(key: Long): TimetableList?

    @Query("DELETE FROM timetable_table WHERE id = :key")
    suspend fun del(key: Long)

    @Query("DELETE FROM timetable_table")
    suspend fun clear()


    @Query("SELECT * FROM timetable_table ORDER BY id DESC")
    fun getAllTimetable(): LiveData<List<TimetableList>>


}