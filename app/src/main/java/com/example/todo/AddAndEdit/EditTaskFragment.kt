package com.example.todo.AddAndEdit

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.example.todo.CONST
import com.example.todo.Formater.dateFormat
import com.example.todo.Formater.timeFormat
import com.example.todo.R
import com.example.todo.clearTime
import com.example.todo.databinding.FragmentEditTaskBinding
import com.route.todoappc40gsat.database.TaskDatabase
import com.route.todoappc40gsat.database.model.Task
import kotlinx.coroutines.flow.combine
import java.text.SimpleDateFormat
import java.util.Calendar

class EditTaskFragment : Fragment() {
lateinit var binding: FragmentEditTaskBinding
lateinit var task: Task
  var calendar: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentEditTaskBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPassedTask()

        bind(task)
        binding.editTextDate.setOnClickListener { showPicker() }
        binding.saveChangeBtn.setOnClickListener{
saveChange()

        }

    }

fun getPassedTask(){
    arguments.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            task=requireArguments().getParcelable(CONST.PASSED_TASK,Task::class.java)?:Task()
        }else {
            task = requireArguments().getParcelable(CONST.PASSED_TASK) ?: Task()
  } }}


    private fun bind(task: Task) {
binding.taskTitle.editText?.setText(task.title)
binding.taskDetails.editText?.setText(task.description)
binding.editTextDate.text=task.date?.dateFormat()
 binding.isDone.isChecked=task.isDone?:false


    }

    fun showPicker() {
        val datePickerDialog = DatePickerDialog(requireContext())

        datePickerDialog.setOnDateSetListener { datePicker, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)

            calendar.get(Calendar.HOUR_OF_DAY)
            calendar.get(Calendar.MINUTE)
            val simpleDateFormat = task.date?.let {
                SimpleDateFormat("yyyy.MM.dd 'at' hh:mm").format(
                    it
                )
            }
            binding.editTextDate.text =simpleDateFormat
        }

         datePickerDialog.show()

    }



     fun saveChange( ){
        calendar.clearTime()

val newTask=Task(
    task.id,
    title =  binding.taskTitle.editText?.text.toString(),
    description =  binding.taskDetails.editText?.text.toString(),
    date = calendar.time,
    isDone= binding.isDone.isChecked)

        TaskDatabase.getInstance(requireContext()).getTaskDao().updateTask(newTask)

    }


    }




