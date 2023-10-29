package com.example.cursoscesae.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.cursoscesae.R
import com.example.cursoscesae.database.DBHelper
import com.example.cursoscesae.databinding.ActivityCourseDetailBinding
import com.example.cursoscesae.model.CourseModel

class CourseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseDetailBinding
    private lateinit var db: DBHelper
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var courseModel = CourseModel()
    private var imageID:Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val id = i.extras?.getInt("id")
        db = DBHelper(applicationContext)

        if (id != null) {
            courseModel = db.getCourse(id)
            populate()
        } else {
            finish()
        }

        binding.btnBack.setOnClickListener{
            setResult(0,i)
            finish()
        }

        binding.btnEdit.setOnClickListener {
            binding.layoutEditDelete.visibility = View.VISIBLE
            binding.layoutEdit.visibility = View.GONE
            changeEditText(true)
        }
        binding.btnCancel.setOnClickListener {
            binding.layoutEditDelete.visibility = View.GONE
            binding.layoutEdit.visibility = View.VISIBLE
            populate()
            changeEditText(false)

        }

        binding.btnSave.setOnClickListener {

            val res = db.updateCourse(
                id = courseModel.id,
                name = binding.editName.text.toString(),
                local = binding.editLocal.text.toString(),
                startDate = binding.editStartDate.text.toString(),
                endDate = binding.editEndDate.text.toString(),
                price = binding.editPrice.text.toString(),
                duration = binding.editDuration.text.toString().toInt(),
                edition= binding.editEdition.text.toString().toInt(),
                imageID = imageID
            )
            if (res > 0) {
                Toast.makeText(applicationContext, "Update OK", Toast.LENGTH_SHORT).show()
                setResult(1, i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Update Error", Toast.LENGTH_SHORT).show()
                setResult(0, i)
                finish()
            }
        }
        binding.btnDelete.setOnClickListener {
            val res = db.deleteCourse(courseModel.id)

            if (res > 0) {
                Toast.makeText(applicationContext, "Delete OK", Toast.LENGTH_SHORT).show()
                setResult(1, i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Delete Error", Toast.LENGTH_SHORT).show()
                setResult(0, i)
                finish()
            }
        }

        binding.imageCourse.setOnClickListener {
            if(binding.editName.isEnabled){
            launcher.launch(Intent(applicationContext, CourseImageActivity::class.java))}
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.data != null && it.resultCode == 1) {
                if (it.data?.extras!=null){
                    imageID = it.data?.getIntExtra("id",0)!!
                    binding.imageCourse.setImageResource(imageID)
                }

            } else {
                imageID = -1

                binding.imageCourse.setImageResource(R.drawable.elearning)
            }
        }
    }

    private fun changeEditText(status: Boolean) {
        binding.editName.isEnabled = status
        binding.editLocal.isEnabled = status
        binding.editStartDate.isEnabled = status
        binding.editEndDate.isEnabled = status
        binding.editPrice.isEnabled = status
        binding.editDuration.isEnabled = status
        binding.editEdition.isEnabled = status



    }

    private fun populate() {


        binding.editName.setText(courseModel.name)
        binding.editLocal.setText(courseModel.local)
        binding.editStartDate.setText(courseModel.startDate)
        binding.editEndDate.setText(courseModel.endDate)
        binding.editPrice.setText(courseModel.price)
        binding.editDuration.setText(courseModel.duration.toString())
        binding.editEdition.setText(courseModel.edition.toString())

        if (courseModel.imageID > 0) {
            binding.imageCourse.setImageResource(courseModel.imageID)
        } else {
            binding.imageCourse.setImageResource(R.drawable.elearning)
        }

    }
}