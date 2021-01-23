package com.tent1s.android.schedule.ui.settings

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.database.ScheduleDatabase
import com.tent1s.android.schedule.databinding.FragmentSettingsBinding
import com.tent1s.android.schedule.ui.timetable.newtimerow.NewTimeRowViewModelFactory
import kotlinx.coroutines.launch


class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: SettingsViewModelFactory

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        _binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_settings,
                container,
                false
        )

        val application = requireNotNull(this.activity).application
        val dataSourceTimetable = ScheduleDatabase.getInstance(application).timetableDatabaseDao
        val dataSourceTasks = ScheduleDatabase.getInstance(application).tasksDatabaseDao
        viewModelFactory = SettingsViewModelFactory(dataSourceTimetable, dataSourceTasks, application)
        settingsViewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        binding.delTasksView.setOnClickListener{
            val title = "Вы уверены?"
            val button1String = "ДА"
            val button2String = "НЕТ"

            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            builder.setTitle(title)


            builder.setPositiveButton(button1String) { _, _ ->
                settingsViewModel.clearTasks()
            }

            builder.setNegativeButton(button2String) { _, _ -> }


            builder.setCancelable(true);
            val dialog: AlertDialog = builder.create()

            dialog.show()
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setBackgroundColor(Color.GRAY)
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundColor(Color.RED)
        }

        binding.delTimetableView.setOnClickListener{
            val title = "Вы уверены?"
            val button1String = "ДА"
            val button2String = "НЕТ"

            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            builder.setTitle(title)


            builder.setPositiveButton(button1String) { _ , _ ->
                settingsViewModel.clearTimetable()
            }

            builder.setNegativeButton(button2String) { _ , _ -> }


            builder.setCancelable(true);
            val dialog: AlertDialog = builder.create()

            dialog.show()
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setBackgroundColor(Color.GRAY)
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundColor(Color.RED)
        }

    }
}