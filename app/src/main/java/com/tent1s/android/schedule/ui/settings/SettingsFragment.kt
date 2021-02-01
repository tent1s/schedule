package com.tent1s.android.schedule.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.ScheduleApplication
import com.tent1s.android.schedule.database.ScheduleDatabase
import com.tent1s.android.schedule.databinding.FragmentSettingsBinding
import com.tent1s.android.schedule.repository.ScheduleRepository


class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var prefs : SharedPreferences
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: SettingsViewModelFactory
    private lateinit var myRepository : ScheduleRepository


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {


        _binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_settings,
                container,
                false
        )

        val application = requireNotNull(this.activity).application

        val firebase = FirebaseDatabase.getInstance().getReference("timetable")
        val dataSourceTasks = ScheduleDatabase.getInstance(application).tasksDatabaseDao

        viewModelFactory = SettingsViewModelFactory(firebase, dataSourceTasks, application)
        settingsViewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)
        myRepository = (requireActivity().application as ScheduleApplication).repository


        prefs = activity!!.getSharedPreferences("settings", Context.MODE_PRIVATE)

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

            MaterialAlertDialogBuilder(context!!, R.style.AlertDialogTheme)
                    .setTitle("Вы уверены?")
                    .setNegativeButton("ДА") { _, _ ->
                        settingsViewModel.clearTasks()
                    }
                    .setPositiveButton("НЕТ") { _, _ -> }
                    .show()

        }

        binding.delTimetableView.setOnClickListener{
            MaterialAlertDialogBuilder(context!!, R.style.AlertDialogTheme)
                    .setTitle("Вы уверены?")
                    .setNegativeButton("ДА") { _, _ ->
                        settingsViewModel.clearTimetable()
                    }
                    .setPositiveButton("НЕТ") { _, _ -> }
                    .show()
        }




        binding.switchTheme.setOnCheckedChangeListener{_, switch: Boolean ->
            val editor = prefs.edit()
            if (switch){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("theme", binding.switchTheme.isChecked).apply()
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("theme", binding.switchTheme.isChecked).apply()
            }
        }




        binding.aboutView.setOnClickListener{
            val navController = binding.root.findNavController()
            navController.navigate(R.id.action_settings_to_aboutMeFragment)
        }

    }


    override fun onResume() {
        super.onResume()

        if(prefs.contains("theme")){
            binding.switchTheme.isChecked = prefs.getBoolean("theme", false)
        }
    }
}