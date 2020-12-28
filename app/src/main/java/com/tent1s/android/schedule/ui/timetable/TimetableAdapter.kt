package com.tent1s.android.schedule.ui.timetable

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tent1s.android.schedule.databinding.HeaderTimetableBinding
import com.tent1s.android.schedule.databinding.ListItemTimetableBinding
import com.tent1s.android.schedule.repository.ScheduleRepository


private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class TimetableAdapter(var list: List<ScheduleRepository.ListItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_VIEW_TYPE_HEADER) {
            ViewHolderHeader.from(parent)
        } else {
            ViewHolderItem.from(parent)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderHeader) {
            val item = list[position] as ScheduleRepository.Header
            holder.bind(item)
        } else if (holder is ViewHolderItem) {
            val item = list[position] as ScheduleRepository.ContentItem
            holder.bind(item)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) ITEM_VIEW_TYPE_HEADER else ITEM_VIEW_TYPE_ITEM
    }

    private fun isPositionHeader(position: Int): Boolean {
        return list[position] is ScheduleRepository.Header
    }


    override fun getItemCount(): Int {
        return list.size
    }



    class ViewHolderHeader private constructor(val binding: HeaderTimetableBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScheduleRepository.Header) {
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




    class ViewHolderItem private constructor(val binding: ListItemTimetableBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScheduleRepository.ContentItem){
            binding.timetableTitle.text = item.title
            binding.timetableInf.text = item.inf
            when(item.colorId) {
                0 -> binding.timetableColor.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
                1 -> binding.timetableColor.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP)
                2 -> binding.timetableColor.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
                3 -> binding.timetableColor.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP)
                4 -> binding.timetableColor.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolderItem {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTimetableBinding.inflate(layoutInflater,parent,false)

                return ViewHolderItem(binding)
            }
        }
    }
}
