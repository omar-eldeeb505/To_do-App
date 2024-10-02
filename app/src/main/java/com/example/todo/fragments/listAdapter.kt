package com.example.todo.fragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todo.R
import com.example.todo.databinding.ItemTaskBinding
import com.route.todoappc40gsat.database.TaskDatabase
import com.route.todoappc40gsat.database.model.Task
import java.text.SimpleDateFormat

class listAdapter(var listTask: MutableList<Task>) : Adapter<listAdapter.listViewHolder>() {
    class listViewHolder(var binding: ItemTaskBinding) : ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(task: Task) {
            binding.taskTitle.text = task.title
            val simpleDateFormat = task.date?.let {
                SimpleDateFormat("yyyy.MM.dd").format(
                    it
                )
            }
            binding.taskDate.text = simpleDateFormat
//            binding.isDone.setOnClickListener{}


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return listViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(task: MutableList<Task>) {
        listTask = task
        notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: listViewHolder, position: Int) {

        val item = listTask[position]
        holder.bind(item)
        holder.binding.dragItem.setOnClickListener {

            onTaskClickListenerObject?.onTaskClick(item, position)

        }

        holder.binding.isDone.setOnClickListener {
            TaskDatabase.getInstance(holder.itemView.rootView.context).getTaskDao().deleteTask(item)
            listTask.removeAt(position)
            notifyItemRemoved(position)
           val toast= Toast.makeText(it.context, "Congratulations",Toast.LENGTH_LONG).show()
            it.setBackgroundResource(R.color.green)
            holder.binding.taskTitle.setTextColor(
                ContextCompat.getColor(
                    it.context,
                    R.color.green
                )
            ) // Set text color when done
            holder.binding.dividerStatus.setBackgroundResource(R.color.green)
//                it.setTextColor(ContextCompat.getColor(it.context, R.color.blue))
            item.isDone = true

        }
            holder.binding.leftItem.setOnClickListener{
            TaskDatabase.getInstance(holder.itemView.rootView.context).getTaskDao().deleteTask(item)
                listTask.removeAt(position)
                notifyItemRemoved(position)

        }

    }


    var onTaskClickListenerObject: OnTaskClickListener? = null


}

interface OnTaskClickListener {

    fun onTaskClick(task: Task, position: Int)
}


