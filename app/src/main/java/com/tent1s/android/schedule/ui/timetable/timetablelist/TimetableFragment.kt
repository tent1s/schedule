package com.tent1s.android.schedule.ui.timetable.timetablelist

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.ScheduleApplication
import com.tent1s.android.schedule.databinding.FragmentTasksBinding
import com.tent1s.android.schedule.databinding.FragmentTimetableBinding
import com.tent1s.android.schedule.repository.ScheduleRepository
import com.tent1s.android.schedule.ui.tasks.TasksAdapter
import com.tent1s.android.schedule.ui.tasks.taskslist.TasksFragmentDirections
import com.tent1s.android.schedule.ui.timetable.TimetableAdapter
import timber.log.Timber

class TimetableFragment : Fragment() {

    private lateinit var timetableViewModel: TimetableViewModel
    private var _binding: FragmentTimetableBinding? = null
    private val binding get() = _binding!!
    private lateinit var myRepository : ScheduleRepository
    private lateinit var prefs : SharedPreferences

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTimetableBinding.inflate(inflater)


        myRepository = (requireActivity().application as ScheduleApplication).repository

        timetableViewModel =
                ViewModelProvider(this).get(TimetableViewModel::class.java)



        myRepository.timetable.observe(viewLifecycleOwner){ list ->
            timetableViewModel.week.observe(viewLifecycleOwner){
                timetableViewModel.getTimetable(list, it)
            }
        }

        prefs = activity!!.getSharedPreferences("settings", Context.MODE_PRIVATE)

        if(prefs.contains("week")){
            timetableViewModel.setWeek( prefs.getInt("week", 0))
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = TimetableAdapter {
            val navController = binding.root.findNavController()
            val week = timetableViewModel.week.value
            if (week != null) {
                navController.navigate(TimetableFragmentDirections.actionNavigationTimetableToNewTimeRow(it.id, week))
            }
        }
        binding.timetableList.adapter = adapter


        timetableViewModel.state.observe(viewLifecycleOwner){
            adapter.setData(it)
            if (it.isEmpty()){
                timetableViewModel.listIsEmpty()
            }else{
                timetableViewModel.listIsNotEmpty()
            }
        }



        binding.floatingActionButtonTimetable.setOnClickListener {
            val navController = binding.root.findNavController()
            val week = timetableViewModel.week.value
            if (week != null) {
                navController.navigate(TimetableFragmentDirections.actionNavigationTimetableToNewTimeRow(-1L, week))
            }
        }



        binding.timetableList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0 && !binding.floatingActionButtonTimetable.isShown) {
                    binding.floatingActionButtonTimetable.show()
                    binding.Week.visibility = View.VISIBLE
                }
                else
                    if (dy > 0 && binding.floatingActionButtonTimetable.isShown) {
                        binding.floatingActionButtonTimetable.hide()
                        binding.Week.visibility = View.GONE
                    }
            }
        })

        binding.textWeek.text = when (timetableViewModel.week.value){
            0 ->  "четная неделя"
            1 ->  "нечетная неделя"
            else -> "error"
        }

        binding.Week.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Выберете неделю")

            val dayOfWeek = arrayOf("Четная неделя", "Нечетная неделя")
            builder.setItems(dayOfWeek) { _, which ->
                when (which){
                    0 -> binding.textWeek.text = "четная неделя"
                    1 -> binding.textWeek.text = "нечетная неделя"
                }
                val editor = prefs.edit()
                editor.putInt("week", which).apply()
                timetableViewModel.setWeek(which)
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        timetableViewModel.checkEmptyList.observe(viewLifecycleOwner) {
            if (it) {
                binding.textTimetable.text = "Нажмите “+”, чтобы добавить"
            }else{
                binding.textTimetable.text = ""
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}