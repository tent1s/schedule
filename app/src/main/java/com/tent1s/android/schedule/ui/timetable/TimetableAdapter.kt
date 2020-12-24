package com.tent1s.android.schedule.ui.timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tent1s.android.schedule.databinding.HeaderTimetableBinding
import com.tent1s.android.schedule.databinding.ListItemTimetableBinding
import com.tent1s.android.schedule.repository.TimetableRepository


private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class TimetableAdapter(var list: List<TimetableRepository.ListItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_VIEW_TYPE_HEADER) {
            ViewHolderHeader.from(parent)
        } else {
            ViewHolderItem.from(parent)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderHeader) {
            val item = list[position] as TimetableRepository.Header
            holder.bind(item)
        } else if (holder is ViewHolderItem) {
            val item = list[position] as TimetableRepository.ContentItem
            holder.bind(item)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) ITEM_VIEW_TYPE_HEADER else ITEM_VIEW_TYPE_ITEM
    }

    private fun isPositionHeader(position: Int): Boolean {
        return list[position] is TimetableRepository.Header
    }


    override fun getItemCount(): Int {
        return list.size
    }



    class ViewHolderHeader private constructor(val binding: HeaderTimetableBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TimetableRepository.Header) {
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

        fun bind(item: TimetableRepository.ContentItem){
            binding.timetableTitle.text = item.title
            binding.timetableInf.text = item.inf
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
