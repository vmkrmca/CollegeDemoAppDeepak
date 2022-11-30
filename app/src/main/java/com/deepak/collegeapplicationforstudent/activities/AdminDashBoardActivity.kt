package com.deepak.collegemanagementapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.deepak.collegeapplicationforstudent.R
import com.deepak.collegemanagementapp.adapter.StudentAdapter
import com.deepak.collegemanagementapp.database.CollegeDBHelper
import com.deepak.collegemanagementapp.listeners.OnStudentClickListener
import com.deepak.collegemanagementapp.models.Student
import com.deepak.collegemanagementappdemo.activities.AdminAddEventActivity

class AdminDashBoardActivity : Activity(),OnStudentClickListener, View.OnClickListener {

    lateinit var lvStudentsData : ListView
    lateinit var studentList : ArrayList<Student>
    lateinit var collegeDBHelper: CollegeDBHelper

    lateinit var tvAddEvent : TextView
    lateinit var tvEventList : TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_dashboard)

        tvAddEvent = findViewById(R.id.tvAddEvent)
        tvEventList = findViewById(R.id.tvEventList)

        collegeDBHelper = CollegeDBHelper(this@AdminDashBoardActivity)
        lvStudentsData = findViewById(R.id.lvStudentsData)
        studentList = ArrayList<Student>()

        tvAddEvent.setOnClickListener(this)
        tvEventList.setOnClickListener(this)


        adapterCalling()



    }

    private fun adapterCalling() {

        studentList.clear()
        studentList = collegeDBHelper.readDataFromStudTBL()
        var studentAdapter = StudentAdapter(this@AdminDashBoardActivity,studentList,this)
        lvStudentsData.adapter = studentAdapter
        (lvStudentsData.adapter as StudentAdapter).notifyDataSetChanged()

    }

    override fun onStudentClick(student: Student) {
        var statusValue = student.studStatus
        if (statusValue == 0){
            statusValue = 1
            collegeDBHelper.updateStudentStatus(student.studMobileNumber,statusValue)
            adapterCalling()
        }

    }

    override fun onClick(v: View?) {

        when(v?.id) {

            R.id.tvAddEvent ->{

                var intent = Intent(this, AdminAddEventActivity :: class.java)
                startActivity(intent)

            }

            R.id.tvEventList ->{

            }

        }
    }

}
