package com.tent1s.android.schedule

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.tent1s.android.schedule.database.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
@LargeTest
class  ScheduleDatabaseTest {

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
    @Throws(Exception::class)
    fun insertAndGetTimetableRow()  {
        runBlocking {
            val timetableRow = TimetableList()
            timetableDao.insert(timetableRow)

            val lastTimetableRow = timetableDao.get(1)

            assertEquals(lastTimetableRow?.timetableColorId, -1)
        }
    }

    @Test
    @Throws(java.lang.Exception::class)
     fun whenUpdateTaskListThenReadTheSameOne() {
        runBlocking {
            val task = TasksList()
            tasksDao.insert(task)
            task.isTaskDone = false
            task.title = "test"
            tasksDao.update(task)
            assertEquals(task?.isTaskDone, false)
            assertEquals(task?.title, "test")
        }
    }

    @Test
    @Throws(Exception::class)
     fun insertAndGetTask() {
       runBlocking {
           val task = TasksList()
           tasksDao.insert(task)

           val lastTask = tasksDao.getLastTask()

           assertEquals(lastTask?.isTaskDone, false)
       }
    }

    @Test
    @Throws(Exception::class)
    fun whenUpdateTimetableListThenReadTheSameOne() {
        runBlocking {
            val timetableRow = TimetableList()
            timetableDao.insert(timetableRow)
            timetableRow.timetableInformation = "mat"
            timetableRow.timetableTitle = "test"
            timetableDao.update(timetableRow)
            assertEquals(timetableRow?.timetableInformation, "mat")
            assertEquals(timetableRow?.timetableTitle, "test")
        }
    }
}

