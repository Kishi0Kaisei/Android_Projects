package com.example.cursoscesae.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cursoscesae.R
import com.example.cursoscesae.adapter.viewholder.CourseListAdapter
import com.example.cursoscesae.adapter.listener.CourseOnClickListener
import com.example.cursoscesae.database.DBHelper
import com.example.cursoscesae.databinding.ActivityMainBinding
import com.example.cursoscesae.model.CourseModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var courseList: List<CourseModel>
    private lateinit var adapter: CourseListAdapter
    private lateinit var result: ActivityResultLauncher<Intent>
    private lateinit var dbHelper: DBHelper

    private var ascDesc: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        val sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)



        binding.recyclerViewCourses.layoutManager = LinearLayoutManager(this)

        loadList()



        /*binding.recyclerViewCourses.adapter = CourseListAdapter(dbHelper.courseList, CourseListAdapter.OnCLickListener { course ->
            val intent = Intent(applicationContext, CourseDetailActivity::class.java)
            intent.putExtra("course", course)
            startActivity(intent)
        })*/

        binding.btnLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("username", "")
            editor.apply()
            finish()
        }
        courseList = dbHelper.getAllCourse()



        binding.btnDate.setOnClickListener {

            if (ascDesc) {
                loadListDates()
            } else {
                courseList = courseList.reversed()
                ascDesc = !ascDesc
            }

        }
        placeAdapter()

        binding.btnAdd.setOnClickListener {
            result.launch(Intent(applicationContext, NewCourseActivity::class.java))
        }

        binding.btnOrder.setOnClickListener {

            if (ascDesc) {
                loadList()
                binding.btnOrder.setImageResource(R.drawable.baseline_arrow_upward_24)
            } else {
                binding.btnOrder.setImageResource(R.drawable.baseline_arrow_downward_24)
            }
            ascDesc = !ascDesc
            courseList = courseList.reversed()

        }
        placeAdapter()

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                loadList()
            } else if (it.data != null && it.resultCode == 0) {
                Toast.makeText(applicationContext, "Operation Cancelled", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun placeAdapter() {
        courseList = dbHelper.getAllCourse()
        adapter = CourseListAdapter(courseList as ArrayList<CourseModel>, CourseOnClickListener { course ->
            val intent = Intent(applicationContext, CourseDetailActivity::class.java)
            intent.putExtra("id", course.id)
            result.launch(intent)
        })
        binding.recyclerViewCourses.adapter = adapter
    }

    private fun loadList() {

        /*courseList = dbHelper.getAllCourse().sortedWith(compareBy { it.name })
        Log.d("MainActivity", "Course list size: ${courseList.size}")
        placeAdapter()*/

        courseList =
            dbHelper.getAllCourse().sortedWith(compareBy { it.name })
        placeAdapter()

    }

    private fun loadListDates() {

        /*courseList = dbHelper.getAllCourse().sortedWith(compareBy { it.startDate })
        Log.d("MainActivity", "Course list size: ${courseList.size}")
        placeAdapter()*/

        courseList =
            dbHelper.coursesSortedDate().sortedWith(compareBy { it.startDate })
        placeAdapter()
    }






}

