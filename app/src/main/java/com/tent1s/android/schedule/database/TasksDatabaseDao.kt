package com.tent1s.android.schedule.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TasksDatabaseDao {

    @Insert
    suspend fun insert(task: TasksList)


    @Update
    suspend fun update(task: TasksList)


    @Query("SELECT * from tasks_list_table WHERE id = :key")
    suspend fun get(key: Long): TasksList?


    @Query("DELETE FROM tasks_list_table")
    suspend fun clear()


    @Query("SELECT * FROM tasks_list_table ORDER BY id DESC")
    fun getAllTasks(): LiveData<List<TasksList>>

    @Query("SELECT * FROM tasks_list_table ORDER BY id DESC LIMIT 1")
    suspend fun getLastTask(): TasksList?


}