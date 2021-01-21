package com.tent1s.android.schedule.ui.tasks


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tent1s.android.schedule.databinding.HeaderTimetableBinding
import com.tent1s.android.schedule.databinding.ListItemTasksBinding

import com.tent1s.android.schedule.utils.convertMonthToString


private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class TasksAdapter(val clickListener: (TasksItem.ContentTask) -> Unit) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list = emptyList<TasksItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_VIEW_TYPE_HEADER) {
            ViewHolderHeader.from(parent)
        } else {
            ViewHolderItem.from(parent)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderHeader) {
            val item = list[position] as TasksItem.HeaderTask
            holder.bind(item)
        } else if (holder is ViewHolderItem) {
            val item = list[position] as TasksItem.ContentTask
            holder.bind(item)
            holder.binding.row.setOnClickListener { clickListener(item) }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) ITEM_VIEW_TYPE_HEADER else ITEM_VIEW_TYPE_ITEM
    }

    private fun isPositionHeader(position: Int): Boolean {
        return when(list[position]){
            is TasksItem.HeaderTask -> true
            is TasksItem.ContentTask -> false
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }



    class ViewHolderHeader private constructor(val binding: HeaderTimetableBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TasksItem.HeaderTask) {
            binding.header.text = item.header
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolderHeader {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HeaderTimetableBinding.inflate(layoutInflater,parent,false)

                return ViewHolderHeader(binding)
            }
        }
    }




    class ViewHolderItem private constructor(val binding: ListItemTasksBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TasksItem.ContentTask){
            binding.taskTitle.text = item.title
            binding.taskInf.text = item.inf
            binding.deadline.text = "${item.taskDeadlineDay} ${convertMonthToString(item.taskDeadlineMount)} ${item.taskDeadlineYear}"
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolderItem {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTasksBinding.inflate(layoutInflater,parent,false)

                return ViewHolderItem(binding)
            }
        }

    }

    fun setData(listData: List<TasksItem>) {
        val tasksDiffUtil = TasksDiffUtil(list, listData)
        val tasksDiffResult = DiffUtil.calculateDiff(tasksDiffUtil)
        this.list = listData
        tasksDiffResult.dispatchUpdatesTo(this)
    }

}

