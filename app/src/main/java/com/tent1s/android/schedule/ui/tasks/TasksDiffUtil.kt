package com.tent1s.android.schedule.ui.tasks

import androidx.recyclerview.widget.DiffUtil
import com.tent1s.android.schedule.ui.tasks.taskslist.TasksItem


class TasksDiffUtil(
        private val oldList: List<TasksItem>,
        private val newList: List<TasksItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition] as TasksItem.ContentTask
        val newItem = newList[newItemPosition] as TasksItem.ContentTask
        return oldItem.id  == newItem.id
    }
}