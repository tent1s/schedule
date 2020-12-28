package com.tent1s.android.schedule.ui.timetable.timetablelist

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class MyDialogFragment : DialogFragment() {

    private val catNames = arrayOf("Васька", "Рыжик", "Мурзик")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Выберите кота")
                    .setItems(catNames
                    ) { dialog, which ->
                        Toast.makeText(activity, "Выбранный кот: ${catNames[which]}",
                                Toast.LENGTH_SHORT).show()
                    }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

