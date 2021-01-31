package com.tent1s.android.schedule.ui.timetable.newtimerow

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tent1s.android.schedule.R
import com.tent1s.android.schedule.database.ScheduleDatabase
import com.tent1s.android.schedule.databinding.FragmentNewTimeRowBinding
import com.tent1s.android.schedule.utils.hideKeyboard
import com.tent1s.android.schedule.utils.shortToast
import com.tent1s.android.schedule.utils.timetableStartTimeToString
import timber.log.Timber


class NewTimeRowFragment : Fragment() {

    private lateinit var newTimeRowViewModel: NewTimeRowViewModel
    private var _binding: FragmentNewTimeRowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: NewTimeRowViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_time_row,
            container,
            false
        )

        val args = arguments?.let { NewTimeRowFragmentArgs.fromBundle(it) }
        val application = requireNotNull(this.activity).application
        val dataSource = ScheduleDatabase.getInstance(application).timetableDatabaseDao

        val firebase = FirebaseDatabase.getInstance().getReference("timetable")

        viewModelFactory = NewTimeRowViewModelFactory(dataSource, application, args!!.timetableId, args.weekId, firebase)
        newTimeRowViewModel =
            ViewModelProvider(this, viewModelFactory).get(NewTimeRowViewModel::class.java)


        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.buttonSave.setOnClickListener {
            if (newTimeRowViewModel.isValid.value == true) {
                newTimeRowViewModel.saveInf()
                val navController = binding.root.findNavController()
                navController.navigate(R.id.action_newTimeRow_to_navigation_timetable)
            }else{
                context?.shortToast("Вы ввели не всю информацию!!")
            }
        }





        binding.buttonDayOfWeek.setOnClickListener {
            activity?.hideKeyboard()
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Выберете день недели")

            val dayOfWeek = arrayOf("Понидельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота")
            builder.setItems(dayOfWeek) { _, which ->
                binding.buttonDayOfWeek.text = newTimeRowViewModel.dayOfWeekIntToString(which)
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        binding.colorButton.setOnClickListener {
            activity?.hideKeyboard()
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Выберете цвет")

            val color = arrayOf("Черный", "Синий", "Зеленый", "Желтый", "Красный")
            builder.setItems(color) { _, which ->
                binding.colorButton.text = newTimeRowViewModel.colorIntToString(which)
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }




        binding.buttonTimeStart.setOnClickListener {
            activity?.hideKeyboard()


            val hour = 0
            val minute = 0
            val tpd = context?.let { it ->
                TimePickerDialog(it, R.style.DialogTheme, TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    binding.buttonTimeStart.text = timetableStartTimeToString(hour,minute)
                }, hour, minute, true)
            }
            tpd?.show()
        }

        binding.buttonTimeEnd.setOnClickListener {
            activity?.hideKeyboard()
            val hour = 0
            val minute = 0
            val tpd = context?.let { it ->
                TimePickerDialog(it, R.style.DialogTheme, TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    binding.buttonTimeEnd.text = timetableStartTimeToString(hour,minute)
                }, hour, minute, true)
            }
            tpd?.show()
        }



        newTimeRowViewModel.errorInvalidTime.observe(viewLifecycleOwner) { display ->
            if (display == true) {
                context?.shortToast("Конечная дата меньше начальной!")
                newTimeRowViewModel.errorInvalidTimeComplete()
            }

        }

        binding.delButton.setOnClickListener {
            newTimeRowViewModel.delButtonOnclick()
            val navController = binding.root.findNavController()
            navController.navigate(R.id.action_newTimeRow_to_navigation_timetable)
        }

        binding.inputTitle.addTextChangedListener( newTimeRowViewModel.titleWatcher() )
        newTimeRowViewModel.titleDatabase.observe(viewLifecycleOwner){
            binding.inputTitle.setText(it)
        }

        binding.inputAbout.addTextChangedListener( newTimeRowViewModel.aboutWatcher() )
        newTimeRowViewModel.aboutLive.observe(viewLifecycleOwner){
            binding.inputAbout.setText(it)
        }

        binding.buttonDayOfWeek.addTextChangedListener( newTimeRowViewModel.dayOfWeekWatcher())
        newTimeRowViewModel.dayOfWeekString.observe(viewLifecycleOwner){
            binding.buttonDayOfWeek.text = it
        }

        binding.buttonTimeStart.addTextChangedListener( newTimeRowViewModel.startTimeWatcher())
        newTimeRowViewModel.startTime.observe(viewLifecycleOwner){
            binding.buttonTimeStart.text = it
        }
        binding.buttonTimeEnd.addTextChangedListener( newTimeRowViewModel.endTimeWatcher())
        newTimeRowViewModel.endTime.observe(viewLifecycleOwner){
            binding.buttonTimeEnd.text = it
        }
        binding.colorButton.addTextChangedListener( newTimeRowViewModel.colorWatcher())
        newTimeRowViewModel.colorString.observe(viewLifecycleOwner){
            binding.colorButton.text = it
        }

        newTimeRowViewModel.title.observe(viewLifecycleOwner){
            if (TextUtils.isEmpty(it)){
                binding.textInputLayout.error = "Поле не лоджно быть пустым"
            }else{
                binding.textInputLayout.error = null
            }
        }

        newTimeRowViewModel.about.observe(viewLifecycleOwner){
            if (TextUtils.isEmpty(it)){
                binding.textInputLayout2.error = "Поле не лоджно быть пустым"
            }else{
                binding.textInputLayout2.error = null
            }
        }


        newTimeRowViewModel.timetable.observe(viewLifecycleOwner){
            newTimeRowViewModel.setVal(it)
        }



    }



}