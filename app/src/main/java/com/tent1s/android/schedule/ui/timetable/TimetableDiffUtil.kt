package com.tent1s.android.schedule.ui.timetable

import androidx.recyclerview.widget.DiffUtil


class TimetableDiffUtil(
        private val oldList: List<TimetableItem>,
        private val newList: List<TimetableItem>
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
        val oldItem = oldList[oldItemPosition] as TimetableItem.ContentItem
        val newItem = newList[newItemPosition] as TimetableItem.ContentItem
        return oldItem.id  == newItem.id
    }
}