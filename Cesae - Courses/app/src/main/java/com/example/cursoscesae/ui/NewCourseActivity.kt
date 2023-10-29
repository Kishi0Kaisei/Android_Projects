package com.example.cursoscesae.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.cursoscesae.R
import com.example.cursoscesae.database.DBHelper
import com.example.cursoscesae.databinding.ActivityCourseDetailBinding
import com.example.cursoscesae.databinding.ActivityNewCourseBinding

class NewCourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewCourseBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var id:Int =-1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityNewCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(applicationContext)
        val i = intent

        binding.btnSave.setOnClickListener {
            val name = binding.editName.text.toString()
            val local = binding.editLocal.text.toString()
            val startDate = binding.editStartDate.text.toString()
            val endDate = binding.editEndDate.text.toString()
            val price = binding.editPrice.text.toString()
            val duration = binding.editDuration.text.toString().toInt()
            val edition = binding.editEdition.text.toString().toInt()
            var imageId = id

            if (id!=null){
                imageId = this.id
                binding.imageCourse.setImageDrawable(resources.getDrawable(imageId))
            }

            if (name.isNotEmpty() && local.isNotEmpty() && price.isNotEmpty()) {
                val res = db.insertCourse(name, local, startDate, endDate, price, duration, edition, imageId)
                if (res > 0) {
                    Toast.makeText(applicationContext, "Insert OK", Toast.LENGTH_SHORT).show()
                    setResult(1,i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Insert Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnCancel.setOnClickListener {
            setResult(0,i)
            finish() }

        binding.imageCourse.setOnClickListener {
            launcher.launch(Intent(applicationContext, CourseImageActivity::class.java))
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

            if (it.data !=null && it.resultCode ==1){
                id = it.data?.extras?.getInt("id")!!
                binding.imageCourse.setImageResource(id!!)
            }else{
                 id = -1

                binding.imageCourse.setImageResource(R.drawable.elearning)
            }
        }
    }
}