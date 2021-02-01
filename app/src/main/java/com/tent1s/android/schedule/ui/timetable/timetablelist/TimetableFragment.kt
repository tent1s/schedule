package com.tent1s.android.schedule.ui.timetable.timetablelist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.ProgressBar
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.ScheduleApplication
import com.tent1s.android.schedule.databinding.FragmentTimetableBinding
import com.tent1s.android.schedule.repository.ScheduleRepository
import com.tent1s.android.schedule.ui.timetable.TimetableAdapter
import com.tent1s.android.schedule.utils.shortToast


class TimetableFragment : Fragment() {

    private lateinit var timetableViewModel: TimetableViewModel
    private var _binding: FragmentTimetableBinding? = null
    private val binding get() = _binding!!
    private lateinit var myRepository : ScheduleRepository
    private lateinit var prefs : SharedPreferences

    private var loadStatus = false


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

            binding.progressBar.visibility = ProgressBar.INVISIBLE
        }


        prefs = activity!!.getSharedPreferences("settings", Context.MODE_PRIVATE)

        if(prefs.contains("week")){
            timetableViewModel.setWeek(prefs.getInt("week", 0))
        }

        myRepository.load.observe(viewLifecycleOwner){
            loadStatus = it
        }
        setHasOptionsMenu(true)
        return binding.root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = TimetableAdapter {
            val navController = binding.root.findNavController()
            val week = timetableViewModel.week.value
            if (week != null) {
                if (loadStatus) {
                    navController.navigate(
                            TimetableFragmentDirections.actionNavigationTimetableToNewTimeRow(
                                    it.id,
                                    week
                            )
                    )
                }else{
                    context?.shortToast("Интернет соединения нету! База данных не загруженна! Вы не можете ничего изменить!")
                }
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
                if (loadStatus) {
                    navController.navigate(
                            TimetableFragmentDirections.actionNavigationTimetableToNewTimeRow(
                                    "",
                                    week
                            )
                    )
                }else{
                    context?.shortToast("Интернет соединения нету! База данных не загруженна! Вы не можете ничего изменить!")
                }
            }
        }



        binding.timetableList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0 && !binding.floatingActionButtonTimetable.isShown) {
                    binding.floatingActionButtonTimetable.show()
                    binding.Week.visibility = View.VISIBLE
                } else
                    if (dy > 0 && binding.floatingActionButtonTimetable.isShown) {
                        binding.floatingActionButtonTimetable.hide()
                        binding.Week.visibility = View.GONE
                    }
            }
        })

        binding.textWeek.text = when (timetableViewModel.week.value){
            0 -> "четная неделя"
            1 -> "нечетная неделя"
            else -> "error"
        }

        binding.Week.setOnClickListener{
            showMenu(it, R.menu.week)
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



    override fun onResume() {
        super.onResume()
        if (!timetableViewModel.isOnline(context!!)) context?.shortToast("Интернет соединения нету!")
    }



    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(context!!, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId){
                R.id.option_1 -> {
                    binding.textWeek.text = "четная неделя"

                    val editor = prefs.edit()
                    editor.putInt("week", 0).apply()
                    timetableViewModel.setWeek(0)

                    true
                }
                R.id.option_2 -> {
                    binding.textWeek.text = "нечетная неделя"

                    val editor = prefs.edit()
                    editor.putInt("week", 1).apply()
                    timetableViewModel.setWeek(1)

                    true
                }
                else -> false
            }

        }
        popup.show()
    }

}





