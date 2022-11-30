package com.deepak.collegemanagementapp.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import com.deepak.collegeapplicationforstudent.R
import com.deepak.collegemanagementapp.database.CollegeDBHelper
import java.util.*

class StudentDashBoardActivity : Activity(), View.OnClickListener {

    private lateinit var etMobileNumber : EditText
    lateinit var etUserName : EditText
    lateinit var etUserPassword : EditText
    lateinit var spCourse : EditText
    lateinit var etUserAge : EditText
    lateinit var tvUpdate : TextView
    lateinit var tvCancel : TextView
    lateinit var tvEvents : TextView
    lateinit var tvLogOut : TextView

    lateinit var collegeDBHelper : CollegeDBHelper
    private var mobileNumber : String = ""
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)
        setContentView(R.layout.activity_student_dashboard)
        sharedPreferences = getSharedPreferences("CollegePrefName", MODE_PRIVATE)
        editor = sharedPreferences.edit()
        mobileNumber = sharedPreferences.getString("MOBILE_NUMBER","").toString()

        tvLogOut = findViewById(R.id.tvLogOut)
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etUserName = findViewById(R.id.etUserName)
        etUserPassword = findViewById(R.id.etUserPassword)
        spCourse = findViewById(R.id.spCourse)
        etUserAge = findViewById(R.id.etUserAge)

        tvUpdate = findViewById(R.id.tvUpdate)
        tvCancel = findViewById(R.id.tvCancel)
        tvEvents = findViewById(R.id.tvEvents)
        tvUpdate.setOnClickListener(this)
        tvCancel.setOnClickListener(this)
        tvEvents.setOnClickListener(this)
        tvLogOut.setOnClickListener(this)

        collegeDBHelper = CollegeDBHelper(this@StudentDashBoardActivity)
        var student = collegeDBHelper.getStudentData(mobileNumber)

        etMobileNumber.setText(mobileNumber)
        etUserName.setText(student.studName)
        etUserPassword.setText(student.studPassword)
        spCourse.setText(student.studCourse)
        etUserAge.setText("${student.studAge}")

    }

    override fun onClick(v: View?) {

        when(v?.id) {

            R.id.tvLogOut ->{
                editor.clear()
                editor.commit()
                var intent = Intent(this,DashBoardActivity ::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
            R.id.tvUpdate ->{

                var mobileNumber = etMobileNumber.text.toString()
                var userName = etUserName.text.toString()
                var userAge = etUserAge.text.toString().toInt()
                var userPassword = etUserPassword.text.toString()

                var id = collegeDBHelper.updateStudentDetails(mobileNumber, userName,userAge,userPassword)
                if (id!=-1){
                    Toast.makeText(this,"Record is Succesfully Updated",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Record is Not Updated",Toast.LENGTH_SHORT).show()
                }
            }

            R.id.tvCancel ->{

            }

            R.id.tvEvents ->{

                var student = collegeDBHelper.getStudentData(mobileNumber)
                var studentCourse = student.studCourse
                var intent = Intent(this@StudentDashBoardActivity,EventStudentListActivity::class.java)
                intent.putExtra("SCourse",studentCourse)
                startActivity(intent)
            }
        }
    }

}
