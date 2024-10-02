package com.example.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.AddAndEdit.AddTaskFragment
import com.example.todo.fragments.ListFragment
import com.example.todo.settingFragment.SettingFragment

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var listFragment:ListFragment
    lateinit var settingFragment:SettingFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listFragment=ListFragment()
        settingFragment=SettingFragment()
        binding.todoBottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.navigation_list -> pushFragment(listFragment)
                R.id.navigation_settings -> pushFragment(settingFragment)

            }
            return@setOnItemSelectedListener true

        }

        pushFragment(ListFragment())
            binding.fabAdd.setOnClickListener {

                val bottomSheetDialogFragment= AddTaskFragment()
                bottomSheetDialogFragment.show(supportFragmentManager,null)

                bottomSheetDialogFragment.onTaskAddedFragmentListenerObject=object :onTaskAddedFragmentListener{
                    override fun onTaskAdded() {
                        if (listFragment.isVisible) {
                            listFragment.getAllTaskFromDatabase()
                        }


                     }
                }

            }
  }

    private fun pushFragment(fragment: Fragment)  {


        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }
}