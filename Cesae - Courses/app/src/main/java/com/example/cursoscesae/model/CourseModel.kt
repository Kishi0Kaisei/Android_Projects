package com.example.cursoscesae.model

data class CourseModel(

    var id: Int = 0,
    var name: String = "",
    var local: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var price: String = "",
    var duration: Int = 0,
    var edition: Int = 0,
    var imageID: Int = -1
) {


    override fun toString(): String {
        return "${name} | ${local} - ${startDate}"
    }
}

