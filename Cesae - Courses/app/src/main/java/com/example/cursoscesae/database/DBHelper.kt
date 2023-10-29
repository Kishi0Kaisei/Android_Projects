package com.example.cursoscesae.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cursoscesae.model.CourseModel
import com.example.cursoscesae.model.UserModel
import kotlin.collections.ArrayList

class DBHelper(context: Context) : SQLiteOpenHelper(context, "database.db", null, 1) {

    /*companion object {
        private const val TABLE_USERS = "users"
        private const val TABLE_COURSES = "courses"
    }*/
    val sql = arrayOf(
        "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT) ",
        "INSERT INTO users(username,password) VALUES ('admin','password') ",
        "INSERT INTO users(username,password) VALUES ('user1','pass1') ",
        "INSERT INTO users(username,password) VALUES ('user2','pass2') ",

        "CREATE TABLE courses(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, local TEXT, startDate TEXT, endDate TEXT, price TEXT, duration INTEGER, edition INTEGER, imageID INTEGER)",
        "INSERT INTO courses(name,local,startDate,endDate,price,duration,edition,imageID) VALUES ('SOFTWARE DEVELOPER','Porto','2023-10-02','2024-06-11','Gratuito',1000,1,-1) ",
        "INSERT INTO courses(name,local,startDate,endDate,price,duration,edition,imageID) VALUES ('BUSINESS INTELLIGENCE C/ POWER BI','Leca da Palmeira','2023-10-10','2024-01-30','Gratuito',175, 1,-1) ",
        "INSERT INTO courses(name,local,startDate,endDate,price,duration,edition,imageID) VALUES ('DATA ANALYST','Lisboa','2023-10-11','2024-06-28','Gratuito',1050,1,-1) ",
        "INSERT INTO courses(name,local,startDate,endDate,price,duration,edition,imageID) VALUES ('DIGITAL OFFICE','Marco de Canaveses','2024-01-15','2024-09-12','Gratuito',950,1,-1) "

    )

    override fun onCreate(db: SQLiteDatabase?) {
        sql.forEach {
            db?.execSQL(it)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    /*----------------------------------------------------------------------------------------------
                                            CRUD USER
    ------------------------------------------------------------------------------------------------*/
    fun insertUser(username: String, password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val res = db.insert("users", null, contentValues)
        db.close()
        return res

    }

    fun updateUser(id: Int, username: String, password: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val res = db.update("users", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun deleteUser(id: Int): Int {
        val db = this.writableDatabase
        val res = db.delete("users", "id=?", arrayOf(id.toString()))
        db.close()
        return res

    }

    fun getUser(username: String, password: String): UserModel {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "SELECT * FROM users WHERE username=? AND password=?",
            arrayOf(username, password)
        )
        var userModel = UserModel()

        if (c.count == 1) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val usernameIndex = c.getColumnIndex("username")
            val passwordIndex = c.getColumnIndex("password")

            val id = c.getInt(idIndex)
            val username = c.getString(usernameIndex)
            val password = c.getString(passwordIndex)

            userModel = UserModel(id, username, password)
        }


        db.close()
        return userModel
    }

    fun login(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "SELECT * FROM users WHERE username=? AND password=?",
            arrayOf(username, password)
        )
        var userModel = UserModel()

        return if (c.count == 1) {
            db.close()
            true

        } else {
            db.close()
            false
        }


    }
    /*----------------------------------------------------------------------------------------------
                                        CRUD COURSES
------------------------------------------------------------------------------------------------*/


    fun insertCourse(
        name: String,
        local: String,
        startDate: String,
        endDate: String,
        price: String,
        duration: Int,
        edition : Int,
        imageID: Int
    ): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("local", local)
        contentValues.put("startDate", startDate)
        contentValues.put("endDate", endDate)
        contentValues.put("price", price)
        contentValues.put("duration", duration)
        contentValues.put("edition",edition)
        contentValues.put("imageID", imageID)
        val res = db.insert("courses", null, contentValues)
        db.close()
        return res

    }

    fun updateCourse(
        id: Int,
        name: String,
        local: String,
        startDate: String,
        endDate: String,
        price: String,
        duration: Int,
        edition: Int,
        imageID: Int
    ): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("local", local)
        contentValues.put("startDate", startDate)
        contentValues.put("endDate", endDate)
        contentValues.put("price", price)
        contentValues.put("duration", duration)
        contentValues.put("edition",edition)
        contentValues.put("imageID", imageID)
        val res = db.update("courses", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun deleteCourse(id: Int): Int {
        val db = this.writableDatabase
        val res = db.delete("courses", "id=?", arrayOf(id.toString()))
        db.close()
        return res

    }

    fun getCourse(id: Int): CourseModel {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "SELECT * FROM courses WHERE id=?",
            arrayOf(id.toString())
        )
        var courseModel = CourseModel()

        if (c.count == 1) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val nameIndex = c.getColumnIndex("name")
            val localIndex = c.getColumnIndex("local")
            val startDateIndex = c.getColumnIndex("startDate")
            val endDateIndex = c.getColumnIndex("endDate")
            val priceIndex = c.getColumnIndex("price")
            val durationIndex = c.getColumnIndex("duration")
            val editionIndex = c.getColumnIndex("edition")
            val imageIDIndex = c.getColumnIndex("imageID")

            courseModel = CourseModel(
                id = c.getInt(idIndex),
                name = c.getString(nameIndex),
                local = c.getString(localIndex),
                startDate = c.getString(startDateIndex),
                endDate = c.getString(endDateIndex),
                price = c.getString(priceIndex),
                duration = c.getInt(durationIndex),
                edition = c.getInt(editionIndex),
                imageID = c.getInt(imageIDIndex)
            )

        }


        db.close()
        return courseModel
    }

    fun getAllCourse(): ArrayList<CourseModel> {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "SELECT * FROM courses", null
        )
        val listCourseModel = ArrayList<CourseModel>()

        if (c.count > 0) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val nameIndex = c.getColumnIndex("name")
            val localIndex = c.getColumnIndex("local")
            val startDateIndex = c.getColumnIndex("startDate")
            val endDateIndex = c.getColumnIndex("endDate")
            val priceIndex = c.getColumnIndex("price")
            val durationIndex = c.getColumnIndex("duration")
            val editionIndex = c.getColumnIndex("edition")
            val imageIDIndex = c.getColumnIndex("imageID")

            do {
                val courseModel = CourseModel(
                    id = c.getInt(idIndex),
                    name = c.getString(nameIndex),
                    local = c.getString(localIndex),
                    startDate = c.getString(startDateIndex),
                    endDate = c.getString(endDateIndex),
                    price = c.getString(priceIndex),
                    duration = c.getInt(durationIndex),
                    edition = c.getInt(editionIndex),
                    imageID = c.getInt(imageIDIndex)
                )
                listCourseModel.add(courseModel)
            } while (c.moveToNext())

        }
        db.close()
        return listCourseModel
    }

    fun coursesSortedDate(): List<CourseModel> {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "SELECT * FROM courses ORDER BY startDate", null
        )
        var listCourseModel = ArrayList<CourseModel>()

        if (c.count > 0) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val nameIndex = c.getColumnIndex("name")
            val localIndex = c.getColumnIndex("local")
            val startDateIndex = c.getColumnIndex("startDate")
            val endDateIndex = c.getColumnIndex("endDate")
            val priceIndex = c.getColumnIndex("price")
            val durationIndex = c.getColumnIndex("duration")
            val editionIndex = c.getColumnIndex("edition")
            val imageIDIndex = c.getColumnIndex("imageID")

            do {
                val courseModel = CourseModel(
                    id = c.getInt(idIndex),
                    name = c.getString(nameIndex),
                    local = c.getString(localIndex),
                    startDate = c.getString(startDateIndex),
                    endDate = c.getString(endDateIndex),
                    price = c.getString(priceIndex),
                    duration = c.getInt(durationIndex),
                    edition = c.getInt(editionIndex),
                    imageID = c.getInt(imageIDIndex)
                )
                listCourseModel.add(courseModel)
            } while (c.moveToNext())

        }
        db.close()
        return listCourseModel
    }
}
