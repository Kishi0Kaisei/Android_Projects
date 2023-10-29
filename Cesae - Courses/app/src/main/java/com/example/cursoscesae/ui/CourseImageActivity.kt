package com.example.cursoscesae.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cursoscesae.R
import com.example.cursoscesae.databinding.ActivityCourseImageBinding

class CourseImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseImageBinding
    private lateinit var i: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        i = intent

        binding.imageCourse1.setOnClickListener { sendID(R.drawable.computer) }
        binding.imageCourse2.setOnClickListener { sendID(R.drawable.computing) }
        binding.imageCourse3.setOnClickListener { sendID(R.drawable.itdepartment) }
        binding.imageCourse4.setOnClickListener { sendID(R.drawable.itsystems) }
        binding.btnRemoveI.setOnClickListener { sendID(R.drawable.elearning) }
    }

    private fun sendID(id: Int) {
        i.putExtra("id", id)
        setResult(1, i)
        finish()
    }
}