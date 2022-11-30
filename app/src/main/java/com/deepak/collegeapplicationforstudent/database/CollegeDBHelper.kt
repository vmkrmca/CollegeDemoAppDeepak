package com.deepak.collegemanagementapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.deepak.collegemanagementapp.models.Student
import com.deepak.collegemanagementappdemo.models.Event

class CollegeDBHelper(context: Context) : SQLiteOpenHelper(context,DB_NAME,null,DB_VER) {

    companion object {

        const val DB_NAME : String = "CollegeDB"
        var DB_VER = 1
        const val TBL_NAME = "StudentTBL"
        const val COL_STUD_MOBILE_NUMBER = "StudMobileNumber"
        const val COL_STUD_NAME = "StudName"
        const val COL_STUD_PASSWORD = "StudPassword"
        const val COL_STUD_GENDER = "StudGender"
        const val COL_STUD_AGE = "StudAge"
        const val COL_STUD_DOB = "StudDOB"
        const val COL_STUD_COURSE = "StudCourse"
        const val COL_STUD_APPROVE_UN_APPROVE = "StudStatus"

        const val EVENT_TBL_NAME = "EventTBL"
        const val COL_EVENT_ID ="eventID"
        const val COL_EVENT_TITLE ="eventTitle"
        const val COL_EVENT_DESCRIPTION ="eventDescription"
        const val COL_EVENT_DATE ="eventDate"
        const val COL_EVENT_TIME ="eventTime"
        const val COL_EVENT_APPROVE_UN_APPROVE = "EventStatus"
        const val COL_STUDENT_EVENT_REQUEST = "eventStudentRequest"

    }

    lateinit var sqLiteDatabase : SQLiteDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TBL_NAME($COL_STUD_MOBILE_NUMBER TEXT PRIMARY KEY,$COL_STUD_NAME TEXT,$COL_STUD_PASSWORD TEXT,$COL_STUD_GENDER TEXT,$COL_STUD_AGE INTEGER,$COL_STUD_DOB TEXT,$COL_STUD_COURSE TEXT,$COL_STUD_APPROVE_UN_APPROVE INTEGER)")
        db?.execSQL("create table $EVENT_TBL_NAME($COL_EVENT_ID TEXT PRIMARY KEY,$COL_EVENT_TITLE TEXT,$COL_EVENT_DESCRIPTION TEXT,$COL_EVENT_DATE TEXT,$COL_EVENT_TIME TEXT,$COL_STUD_MOBILE_NUMBER TEXT,$COL_EVENT_APPROVE_UN_APPROVE INTEGER,$COL_STUD_COURSE TEXT,$COL_STUDENT_EVENT_REQUEST INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}


    fun insertStudRecord(student: Student) : Long {
        sqLiteDatabase = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COL_STUD_MOBILE_NUMBER,student.studMobileNumber)
        contentValues.put(COL_STUD_NAME,student.studName)
        contentValues.put(COL_STUD_PASSWORD,student.studPassword)
        contentValues.put(COL_STUD_DOB,student.studDOB)
        contentValues.put(COL_STUD_COURSE,student.studCourse)
        contentValues.put(COL_STUD_AGE,student.studAge)
        contentValues.put(COL_STUD_GENDER,student.studGender)
        contentValues.put(COL_STUD_APPROVE_UN_APPROVE,student.studStatus)
        return sqLiteDatabase.insert(TBL_NAME,null,contentValues)
    }

    fun insertEventData(event: Event) : Long {

        sqLiteDatabase = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COL_EVENT_ID,event.eventID)
        contentValues.put(COL_EVENT_TITLE,event.eventTitle)
        contentValues.put(COL_EVENT_DESCRIPTION,event.eventDescription)
        contentValues.put(COL_EVENT_DATE,event.eventDate)
        contentValues.put(COL_EVENT_TIME,event.eventTime)
        contentValues.put(COL_STUD_MOBILE_NUMBER,event.eventStudMobileNumber)
        contentValues.put(COL_EVENT_APPROVE_UN_APPROVE,event.eventStatus)
        contentValues.put(COL_STUD_COURSE,event.studentCourse)
        contentValues.put(COL_STUDENT_EVENT_REQUEST,event.eventRequest)
        return sqLiteDatabase.insert(EVENT_TBL_NAME,null,contentValues)

    }

    fun readDataFromStudTBL() : ArrayList<Student> {

        var studentList = ArrayList<Student>()
        sqLiteDatabase = this.readableDatabase

        var cursor = sqLiteDatabase.rawQuery("select * from $TBL_NAME",null)

        if (cursor != null && cursor.count > 0) {

            if (cursor.moveToFirst()){
                do {

                    var studMobileNumber = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_MOBILE_NUMBER))
                    var studName = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_NAME))
                    var studGender = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_GENDER))
                    var studCourse = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_COURSE))
                    var studPassword = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_PASSWORD))
                    var studAge = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_AGE))
                    var studDOB = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_DOB))
                    var studStatus = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_APPROVE_UN_APPROVE))
                    studentList.add(Student(studMobileNumber,studName,studPassword,studGender,studCourse,studDOB,studAge.toInt(),studStatus.toInt()))

                }while (cursor.moveToNext())
            }

        }
        return studentList
    }


    fun readDataFromEventTBL(studCourse : String) : ArrayList<Event> {

        var eventList = ArrayList<Event>()
        sqLiteDatabase = this.readableDatabase

        var cursor = sqLiteDatabase.rawQuery("select * from $EVENT_TBL_NAME where $COL_STUD_COURSE=?", arrayOf(studCourse))

        if (cursor != null && cursor.count > 0) {

            if (cursor.moveToFirst()){
                do {

                    var eventID = cursor.getString(cursor.getColumnIndexOrThrow(COL_EVENT_ID))
                    var eventTitle = cursor.getString(cursor.getColumnIndexOrThrow(COL_EVENT_TITLE))
                    var eventDescription = cursor.getString(cursor.getColumnIndexOrThrow(COL_EVENT_DESCRIPTION))
                    var eventDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_EVENT_DATE))
                    var eventTime = cursor.getString(cursor.getColumnIndexOrThrow(COL_EVENT_TIME))
                    var studentMobileNumber = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_MOBILE_NUMBER))
                    var studentRequest = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_EVENT_REQUEST))
                    eventList.add(Event(eventID,eventTitle,eventDescription,eventDate,eventTime,studentMobileNumber,0,studCourse,studentRequest.toInt()))

                }while (cursor.moveToNext())
            }

        }
        return eventList
    }



    fun updateStudentStatus(studentMobileNumber : String,studentStatus : Int) {
        sqLiteDatabase = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COL_STUD_APPROVE_UN_APPROVE,studentStatus)
        sqLiteDatabase.update(TBL_NAME,contentValues,"$COL_STUD_MOBILE_NUMBER=?", arrayOf(studentMobileNumber))
    }

   fun checkMobileNumberAndPassword(studentMobileNumber: String,studentPassword : String) : Boolean {
       sqLiteDatabase = this.readableDatabase
       var isLoggedInState = false
       var cursor = sqLiteDatabase.rawQuery("select * from $TBL_NAME where $COL_STUD_MOBILE_NUMBER=? AND $COL_STUD_PASSWORD=?", arrayOf(studentMobileNumber,studentPassword))
       if (cursor != null && cursor.count > 0 && cursor.moveToFirst()){
           isLoggedInState = true
       }
       return isLoggedInState
   }

    fun checkApprovalStatus(studentMobileNumber: String)  : Int {
        var studStatus = 0
        sqLiteDatabase = this.readableDatabase
        var cursor = sqLiteDatabase.rawQuery("select * from $TBL_NAME where $COL_STUD_MOBILE_NUMBER=?", arrayOf(studentMobileNumber))
        if (cursor != null && cursor.count > 0 && cursor.moveToFirst()) {
            studStatus = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_APPROVE_UN_APPROVE)).toInt()
            return studStatus
        }
        return studStatus
    }

    fun getStudentData(mobileNumber : String) : Student {

        var student : Student? = null
        sqLiteDatabase = this.readableDatabase
        var cursor = sqLiteDatabase.rawQuery("select * from $TBL_NAME where $COL_STUD_MOBILE_NUMBER=?", arrayOf(mobileNumber))

        if (cursor != null && cursor.count > 0 && cursor.moveToFirst()){

            do {
                var studName = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_NAME))
                var studGender = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_GENDER))
                var studCourse = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_COURSE))
                var studPassword = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_PASSWORD))
                var studAge = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_AGE))
                var studDOB = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUD_DOB))
                student = Student(mobileNumber,studName,studPassword,studGender,studCourse,studDOB,studAge.toInt(),0)
            }while (cursor.moveToNext())
        }
        return student!!
    }

    fun updateStudentDetails(mobileNumber : String, userName : String,userAge : Int,userPassword: String) : Int {
        sqLiteDatabase = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COL_STUD_NAME,userName)
        contentValues.put(COL_STUD_AGE,userAge)
        contentValues.put(COL_STUD_PASSWORD,userPassword)
        return sqLiteDatabase.update(TBL_NAME,contentValues,"$COL_STUD_MOBILE_NUMBER=?", arrayOf(mobileNumber))
    }
}