package com.deepak.collegemanagementapp.activities

import android.app.Activity
import android.os.Bundle
import android.widget.ListView
import com.deepak.collegeapplicationforstudent.R
import com.deepak.collegemanagementapp.database.CollegeDBHelper
import com.deepak.collegemanagementappdemo.models.Event

class EventStudentListActivity : Activity() {

    lateinit var lvEvents : ListView
    var SCourse = ""

    var eventList = ArrayList<Event>()

    lateinit var dbHelper : CollegeDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_event)
        SCourse = intent.extras?.getString("SCourse","").toString()
        dbHelper = CollegeDBHelper(this)

        lvEvents = findViewById(R.id.lvEvents)

        eventList = dbHelper.readDataFromEventTBL(SCourse)

        /*
            EventID
            EventTitle
            EventDec
            course
            MobileNumber



         */

    }

    private fun getStudentEvents(sCourse: String) {


    }
}
