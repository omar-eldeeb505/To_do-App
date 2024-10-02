package com.example.todo.AddAndEdit

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.clearTime
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.onTaskAddedFragmentListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todoappc40gsat.database.TaskDatabase
import com.route.todoappc40gsat.database.model.Task
import java.util.Calendar


class AddTaskFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddTaskBinding
    lateinit var calendar: Calendar
    var onTaskAddedFragmentListenerObject: onTaskAddedFragmentListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()
        binding.selectedDate.setOnClickListener { showPicker() }
        binding.DateTV.setOnClickListener { showPicker() }
        binding.addTaskButton.setOnClickListener {
            ShowDatePickerDialog()

        }

    }
    private fun validate(): Boolean {
        var isValidate = true
        val taskTitle = binding.taskDetails.editText?.text
        val taskDescription = binding.descriptionTextInputLayout.editText?.text
        if (taskTitle == null) {

            binding.taskDetails.error = "Required"
            isValidate = false
        } else {
            binding.taskDetails.error = null
        }
        if (taskDescription == null) {

            binding.descriptionTextInputLayout.error = "Required"
            isValidate = false
        } else {
            binding.descriptionTextInputLayout.error = null
        }


        return isValidate


    }



    private fun ShowDatePickerDialog() {
        calendar.clearTime()
        if (validate()) {

            val task = Task(
                title = binding.taskDetails.editText?.text.toString(),
                description = binding.descriptionTextInputLayout.editText?.text.toString(),
                date = calendar.time,
                isDone = false
            )

            TaskDatabase.getInstance(requireContext()).getTaskDao().insertTask(task)
            dismiss()
            onTaskAddedFragmentListenerObject?.onTaskAdded()
        }
    }

    @SuppressLint("SetTextI18n")
    fun showPicker() {
        val datePickerDialog = DatePickerDialog(requireContext())

        datePickerDialog.setOnDateSetListener { datePicker, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            binding.DateTV.text = "$day/${month + 1}/$year"
        }
        datePickerDialog.show()

    }

}