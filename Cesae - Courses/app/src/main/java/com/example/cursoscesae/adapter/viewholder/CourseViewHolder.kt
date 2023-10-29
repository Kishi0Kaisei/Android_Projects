package com.example.cursoscesae.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cursoscesae.R

class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val image: ImageView = view.findViewById(R.id.image_course)
    val textName: TextView = view.findViewById(R.id.text_name)
    val textLocal: TextView = view.findViewById(R.id.text_local)
    val textStartDate: TextView = view.findViewById(R.id.text_date)
}