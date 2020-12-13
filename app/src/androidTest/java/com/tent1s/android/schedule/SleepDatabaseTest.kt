package com.tent1s.android.schedule

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tent1s.android.schedule.database.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var timetableDao: TimetableDatabaseDao
    private lateinit var tasksDao: TasksDatabaseDao
    private lateinit var db: ScheduleDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, ScheduleDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        tasksDao = db.tasksDatabaseDao
        timetableDao = db.timetableDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(java.lang.Exception::class)
    suspend fun whenUpdateTaskListThenReadTheSameOne() {
        val task = TasksList()
        tasksDao.insert(task)
        task.taskDiligence = 2
        task.taskTitle = "test"
        tasksDao.update(task)
        assertEquals(task?.taskDiligence, 2)
        assertEquals(task?.taskTitle, "test")
    }

    @Test
    @Throws(Exception::class)
    suspend fun insertAndGetTask() {
        val task = TasksList()
        tasksDao.insert(task)

        val lastTask = tasksDao.getLastTask()

        assertEquals(lastTask?.taskDiligence, -1)
    }


    @Test
    @Throws(Exception::class)
    suspend fun insertAndGetTimetableRow()  {
        val timetableRow = TimetableList()
        timetableDao.insert(timetableRow)

        val lastTimetableRow = timetableDao.get(0)

        assertEquals(lastTimetableRow?.timetableColorId, -1)
    }
}

