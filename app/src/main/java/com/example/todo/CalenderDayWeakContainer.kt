package com.example.todo

import android.view.View
import android.widget.TextView
import com.example.todo.databinding.ItemCalenderDayBinding
import com.kizitonwose.calendar.view.ViewContainer

class CalenderDayWeakContainer(var itemCalenderDayView:View): ViewContainer(itemCalenderDayView) {
    val dayWeakTV:TextView=itemCalenderDayView.findViewById<TextView>(R.id.day_of_week)
    val dayMonthTV:TextView=itemCalenderDayView.findViewById<TextView>(R.id.day_of_month)
}