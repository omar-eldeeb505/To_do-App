package com.example.todo.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import com.example.todo.AddAndEdit.EditTaskFragment
import com.example.todo.CONST
import com.example.todo.CalenderDayWeakContainer
import com.example.todo.R
import com.example.todo.clearTime
import com.example.todo.databinding.FragmentListBinding
import com.example.todo.onTaskAddedFragmentListener
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.WeekDayBinder
import com.route.todoappc40gsat.database.TaskDatabase
import com.route.todoappc40gsat.database.model.Task
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale
import java.util.Objects


class ListFragment : Fragment() {
  lateinit var binding: FragmentListBinding
  lateinit var adapter: listAdapter
  lateinit var calendar: Calendar
    var selectedDate: WeekDay? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendar=Calendar.getInstance()
        initCalender()

val fragment=EditTaskFragment()
        getAllTaskFromDatabase()
        adapter.onTaskClickListenerObject=object :OnTaskClickListener{
            override fun onTaskClick(task: Task, position: Int) {
                if (activity==null)return
                 val bundle=Bundle()
                bundle.putParcelable(CONST.PASSED_TASK,task )
fragment.arguments=bundle
                 parentFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("").commit()

            }
        }





}

    private fun initCalender() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.customCalenderView.dayBinder =
                object : WeekDayBinder<CalenderDayWeakContainer> {
                    override fun bind(container: CalenderDayWeakContainer, data: WeekDay) {

                        container.dayWeakTV.text =
                            data.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                        ////////
                        val black = ResourcesCompat.getColor(
                            resources, R.color.black, null
                        )
                        if (selectedDate == data) {
                            val selectedColor =
                                ResourcesCompat.getColor(resources, R.color.blue, null)
                            container.dayWeakTV.setTextColor(selectedColor)
                            container.dayMonthTV.setTextColor(selectedColor)
                        } else {
                            container.dayWeakTV.setTextColor(black)
                            container.dayMonthTV.setTextColor(black)
                        }
                        container.dayMonthTV.text = "${data.date.dayOfMonth}"

                        container.itemCalenderDayView.setOnClickListener {
                            selectedDate = data
                            binding.customCalenderView.notifyWeekChanged(data)


                            /////////
                            container.dayMonthTV.text = "${data.date.dayOfMonth}"

                            val dayOfMonth = data.date.dayOfMonth
                            val month = data.date.month.value - 1
                            val year = data.date.year

                            calendar.set(Calendar.YEAR, year)
                            calendar.set(Calendar.MONTH, month)
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            calendar.clearTime()
                            val filterList = TaskDatabase.getInstance(requireContext()).getTaskDao()
                                .getTasksByDate(calendar.time)
                            adapter.updateData(filterList.toMutableList())

                        }
                    }


                    override fun create(view: View): CalenderDayWeakContainer {
                        return CalenderDayWeakContainer(view)

                    }
                }


            ////////
            val currentDate = LocalDate.now()
            val currentMonth = YearMonth.now()
            val startDate = currentMonth.minusMonths(100).atStartOfMonth() // Adjust as needed
            val endDate = currentMonth.plusMonths(100).atEndOfMonth() // Adjust as needed
            val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
            binding.customCalenderView.setup(startDate, endDate, firstDayOfWeek)
            binding.customCalenderView.scrollToWeek(currentDate)

        }
    }

    fun getAllTaskFromDatabase(){
      val tasks=  TaskDatabase.getInstance(requireContext()).getTaskDao().getAllTasks()
        adapter= listAdapter(tasks.toMutableList())
        binding.listRecyclerView.adapter=adapter
     }



}