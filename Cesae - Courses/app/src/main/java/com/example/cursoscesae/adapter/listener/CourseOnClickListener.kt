package com.example.cursoscesae.adapter.listener

import com.example.cursoscesae.model.CourseModel

class CourseOnClickListener (val clickListener: (course: CourseModel) -> Unit) {
    fun onClick(id:Int, course: CourseModel) = clickListener
}
