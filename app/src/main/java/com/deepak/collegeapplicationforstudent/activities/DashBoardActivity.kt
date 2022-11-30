package com.deepak.collegemanagementapp.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.deepak.collegeapplicationforstudent.R

class DashBoardActivity : Activity(), View.OnClickListener {

    // Declaration Of View Components
    lateinit var tvAdmin : TextView
    lateinit var tvStudent : TextView

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sharedPreferences = getSharedPreferences("CollegePrefName", MODE_PRIVATE)

        tvAdmin = findViewById(R.id.tvAdmin)
        tvStudent = findViewById(R.id.tvStudent)

        tvAdmin.setOnClickListener(this@DashBoardActivity)
        tvStudent.setOnClickListener(this@DashBoardActivity)
    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.tvAdmin -> {

                startActivity(Intent(this@DashBoardActivity, AdminLoginActivity :: class.java))

            }
            R.id.tvStudent -> {
                var mobileNumber = sharedPreferences.getString("MOBILE_NUMBER","")
                if (mobileNumber?.isNotEmpty() == true){
                    startActivity(Intent(this@DashBoardActivity, StudentDashBoardActivity :: class.java))
                }else{
                    startActivity(Intent(this@DashBoardActivity, StudentLoginActivity :: class.java))
                }
            }
        }
    }
}