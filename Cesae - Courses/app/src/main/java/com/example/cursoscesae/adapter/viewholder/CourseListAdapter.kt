package com.example.cursoscesae.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cursoscesae.R
import com.example.cursoscesae.adapter.listener.CourseOnClickListener
import com.example.cursoscesae.adapter.viewholder.CourseViewHolder
import com.example.cursoscesae.model.CourseModel

class CourseListAdapter(
    private val courseList: List<CourseModel>,
    private val courseOnClickListener: CourseOnClickListener
) : RecyclerView.Adapter<CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courseList[position]

        holder.textName.text = course.name
        holder.textLocal.text = course.local
        holder.textStartDate.text = course.startDate

        /*when(course.imageID){
            1 -> {holder.image.setImageResource(R.drawable.computer)}
            2 ->{holder.image.setImageResource(R.drawable.computing)}
            3 ->{holder.image.setImageResource(R.drawable.itdepartment)}
            4 ->{holder.image.setImageResource(R.drawable.itsystems)}
            else ->{holder.image.setImageResource(R.drawable.elearning)}
        }


       */ if (course.imageID > 0) {
            holder.image.setImageResource(course.imageID)
        } else {
            holder.image.setImageResource(R.drawable.elearning)
        }
        holder.itemView.setOnClickListener {
            courseOnClickListener.clickListener(course)
        }
    }
}